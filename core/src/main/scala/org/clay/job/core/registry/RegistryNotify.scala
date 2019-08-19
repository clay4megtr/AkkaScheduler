package org.clay.job.core.registry

import org.clay.job.core.domain.Node
import org.clay.job.core.registry.RegistryEvent.RegistryEvent


/**
  * 注册中心通知接口
  */
trait RegistryNotify {

  /**
    * this:Registry 要求RegistryNotify在实例化时或定义RegistryNotify的子类时，必须混入指定的Registry类型，这个Registry类型也可以指定为当前类型
    * 如果是 self => 则表示给this起了一个别名，叫self，这在有内部类时用的比较多，
    */
  this:Registry =>

  /**
    * 监听器列表
    */
  protected var nodeListener:Array[RegistryListener] = Array.empty[RegistryListener]

  /**
    * 添加监听器
    * @param listener  待添加的节点监听器
    */
  def subscribe(listener: RegistryListener):Unit = {
    this.nodeListener +: nodeListener
  }

  /**
    * 删除一个监听器
    */
  def unSubscribe(listener: RegistryListener):Unit = {
    this.nodeListener.filter(_==listener)
  }

  /**
    * 触发通知
    * @param node     触发通知的节点
    * @param event    触发通知的事件
    */
  def notify(node:Node,event: RegistryEvent):Unit = {
    nodeListener.filter(_.filter(node,event)).foreach(_.onEvent(node,event))
  }
}