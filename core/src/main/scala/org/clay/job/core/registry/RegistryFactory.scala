package org.clay.job.core.registry

import com.typesafe.config.Config
import org.clay.job.core.registry.zookeeper.ZookeeperRegistry
import scala.util.Try

/**
  * 注册中心工厂类
  */
object RegistryFactory {

  /**
    * 返回一个注册中心实例
    * @param config 当前配置
    * @return 注册中心实例
    */
  def getRegistry(config:Config):Try[AbstractRegistry] = Try{

    val registryType = config.getString("registry.type")
    val registryConfig = config.getConfig(s"registry.${registryType}")

    registryType.toLowerCase match {
      case "zookeeper" =>
        new ZookeeperRegistry(registryType,registryConfig)
      case otherType =>
        throw new IllegalArgumentException(s"unsupported registry type $otherType")
    }
  }
}