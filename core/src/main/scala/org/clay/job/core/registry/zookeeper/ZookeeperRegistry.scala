package org.clay.job.core.registry.zookeeper

import java.util.concurrent.TimeUnit

import com.typesafe.config.Config
import org.apache.curator.framework.imps.CuratorFrameworkState
import org.apache.curator.framework.{CuratorFramework, CuratorFrameworkFactory}
import org.apache.curator.framework.recipes.cache.{PathChildrenCache, PathChildrenCacheEvent, PathChildrenCacheListener}
import org.apache.curator.retry.ExponentialBackoffRetry
import org.apache.curator.utils.ZKPaths
import org.apache.zookeeper.CreateMode
import org.apache.zookeeper.KeeperException.NodeExistsException
import org.apache.zookeeper.data.Stat
import org.clay.job.core.domain.Node
import org.clay.job.core.registry.{AbstractRegistry, RegistryEvent, RegistryNotify}
import org.clay.job.utils.ExternalClassHelper._

import scala.collection.JavaConverters._

object ZookeeperRegistry {
  private val slashPlaceholder = "_" //zookeeper节点路径中斜杠字符的替换字符
  private val KEY_HOST = "hosts"
  private val KEY_BASE_SLEEP_TIME_MS = "exponential-backoff-retry.base-sleep-timeMs"
  private val KEY_MAX_RETRIES = "exponential-backoff-retry.max-retries"
  private val KEY_ROOT_PATH = "root-path"
}

/**
  * zookeeper 注册中心
  */
class ZookeeperRegistry(val registryType: String, config: Config) extends AbstractRegistry(registryType, config) {

  private val hosts: String = config.getStringOr(ZookeeperRegistry.KEY_HOST, "localhost:2181")
  private val baseSleepTimeMs: Int = config.getIntOr(ZookeeperRegistry.KEY_BASE_SLEEP_TIME_MS, 1000)
  private val maxRetries: Int = config.getIntOr(ZookeeperRegistry.KEY_MAX_RETRIES, 3)
  private val rootPath: String = config.getStringOr(ZookeeperRegistry.KEY_ROOT_PATH, "/zkClientHelper")
  private var zkClient: CuratorFramework = _
  private var zkPathChildrenCache: PathChildrenCache = _

  //检查根节点是否存在，不存在就创建
  private def checkRootNode(): Unit = {
    val nodeCheck = zkClient.checkExists().forPath(s"${rootPath}")
    if (nodeCheck == null) {
      try {
        zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(s"${rootPath}", "".getBytes())
      } catch {
        case _: NodeExistsException =>
      }
    }
  }

  //root / type - value  ->   Node(type,value) 注意要把value中的下划线替换会斜杠，因为存的时候把节点值里的斜杠替换为了下划线，这是因为zk是用斜杠做路径区分的，不替换的话节点值会变成多级
  def getLeafNodeByType(nodeType: String): Array[Node] = {
    try {
      val leafNodes = zkClient.getChildren.forPath(s"${rootPath}").asScala.map{ child =>
        val children = child.split("-")
        (children(0),children(1))
      }.filter(_._1 == nodeType).map( leafNode => Node(leafNode._1,leafNode._2.replaceAll(ZookeeperRegistry.slashPlaceholder,ZKPaths.PATH_SEPARATOR))).toArray
      leafNodes
    }catch {
      case exception: Exception =>
        Array.empty
    }
  }

  // root / type - value
  //节点值anchor里的 / 需要替换成 _ 下划线，防止和zk的层级弄混
  private def pathFor(registerType:String,anchor:String):String = {
    var anchorChild = anchor.replaceAll(ZKPaths.PATH_SEPARATOR,ZookeeperRegistry.slashPlaceholder)
    s"$rootPath/$registerType-$anchorChild"
  }

  //注册节点，root / type - value
  private def register(registerType:String,anchor:String) = {
    val path = pathFor(registerType,anchor)
    var isSuccess = false
    try {
      val pathCheck:Stat = zkClient.checkExists().forPath(path)
      if(pathCheck != null){
        zkClient.delete().forPath(path)
      }
      zkClient.create().withMode(CreateMode.EPHEMERAL).forPath(path,anchor.getBytes())
      isSuccess = true
    }catch {
      case ex: Exception =>
        ex.printStackTrace()
    }
    isSuccess
  }

  //取消注册
  private def unRegister(registerType:String,anchor:String):Unit = {
    val path = pathFor(registerType,anchor)
    try{
      val pathCheck = zkClient.checkExists().forPath(path)
      if( pathCheck != null ){
        zkClient.delete().forPath(path)
      }
    }catch {
      case ex:Exception =>
    }
  }

  /**
    * 开始连接注册中心
    */
  override def connect(): Unit = {
    if (!isConnected) {
      zkClient = CuratorFrameworkFactory.newClient(hosts, new ExponentialBackoffRetry(baseSleepTimeMs, maxRetries))
      zkClient.start()
      zkPathChildrenCache = new PathChildrenCache(zkClient, rootPath, true)
      zkPathChildrenCache.getListenable.addListener(new MyPathChildrenCacheListener(this))
      zkPathChildrenCache.start()
      zkClient.blockUntilConnected(baseSleepTimeMs,TimeUnit.SECONDS)
      checkRootNode()
    }
  }

  /**
    * 断开和注册中心的连接
    */
  override def disConnect(): Unit = {
    zkPathChildrenCache.getListenable().clear()
    zkPathChildrenCache.clear()
    zkPathChildrenCache.close()
    zkClient.close()
  }

  /**
    * 断开并重新链接注册中心
    */
  override def reConnect(): Unit = {
    disConnect()
    connect()
  }

  /**
    * 与注册中心是否已经连接
    * @return true -> 已经连接
    */
  override def isConnected: Boolean = zkClient != null && zkClient.getState == CuratorFrameworkState.STARTED

  /**
    * 注册节点
    * @param node 待注册的节点
    * @return 注册结果，true表示注册成功
    */
  override def registerNode(node: Node): Boolean = register(node.nodeType,node.anchor)

  /**
    * 注销节点
    * @param node 待注销的节点
    */
  override def unRegisterNode(node: Node): Unit = unRegister(node.nodeType,node.anchor)

  /**
    * 按照节点类型返回节点，
    * @param nodeType 节点类型
    * @return 该类型全部节点
    */
  override def getNodesByType(nodeType: String): Array[Node] = getLeafNodeByType(nodeType)

  /**
    * 返回全部节点，包括节点类型，节点值，
    * 返回之前要把节点值中的 _ 替换回 / ，因为加入的时候把节点值的 / 替换为了 _
    * @return 全部节点
    */
  override def getAllNodes(): Array[Node] = {
    try {
      zkClient.getChildren.forPath(rootPath).asScala.map{typeAndAnchor =>
        val nodeType = typeAndAnchor.split("-")(0)
        val nodeAnchor = typeAndAnchor.split("-")(1)
        Node(nodeType,nodeAnchor.replaceAll(ZKPaths.PATH_SEPARATOR,ZookeeperRegistry.slashPlaceholder))
      }.toArray
    }catch {
      case ex:Exception =>
        Array.empty
    }
  }

  //zkPathChildrenCache 的监听器，监听到节点加入和离开后，使用通知机制通知注册到注册中心的listener
  class MyPathChildrenCacheListener(notify: RegistryNotify) extends PathChildrenCacheListener {

    private def getNode(event: PathChildrenCacheEvent): Node = {
      val nodeTypeAndAnchor = ZKPaths.getPathAndNode(event.getData.getPath).getNode().split("-")
      Node(nodeTypeAndAnchor(0), nodeTypeAndAnchor(1))
    }

    override def childEvent(curatorFramework: CuratorFramework, event: PathChildrenCacheEvent): Unit = {
      event.getType match {
        case PathChildrenCacheEvent.Type.CHILD_ADDED =>
          notify.notify(getNode(event), RegistryEvent.JOIN)
        case PathChildrenCacheEvent.Type.CHILD_REMOVED =>
          notify.notify(getNode(event), RegistryEvent.LEAVE)
        case PathChildrenCacheEvent.Type.CHILD_UPDATED =>
        case PathChildrenCacheEvent.Type.CONNECTION_SUSPENDED =>
        case PathChildrenCacheEvent.Type.CONNECTION_RECONNECTED =>
        case PathChildrenCacheEvent.Type.CONNECTION_LOST =>
        case PathChildrenCacheEvent.Type.INITIALIZED =>
      }
    }
  }

}