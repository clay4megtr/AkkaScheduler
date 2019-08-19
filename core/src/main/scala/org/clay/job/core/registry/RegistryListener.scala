package org.clay.job.core.registry

import org.clay.job.core.domain.Node
import org.clay.job.core.registry.RegistryEvent.RegistryEvent


/**
  * 注册中心的监听器
  */
trait RegistryListener {

  /**
    * 检测当前节点的事件是否满足监听条件
    * 满足监听条件的会调用onEvent函数
    * @param node  事件对应的节点
    * @param event  发生的事件
    * @return   true表示满足监听条件
    */
  def filter(node:Node,event:RegistryEvent):Boolean

  /**
    * 事件回调函数
    * @param node   对应的节点
    * @param event  发生的事件
    */
  def onEvent(node:Node,event: RegistryEvent):Unit
}