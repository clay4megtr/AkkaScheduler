package org.clay.job.core.registry

import org.clay.job.core.domain.Node


/**
  * 节点注册接口
  */
trait Registry extends RegistryNotify {

  /**
    * 获取当前注册中心的类型
    * @return 注册中心的类型
    */
  def registryType:String

  /**
    * 开始连接注册中心
    */
  def connect():Unit

  /**
    * 断开和注册中心的连接
    */
  def disConnect():Unit

  /**
    * 断开并重新链接注册中心
    */
  def reConnect():Unit

  /**
    * 与注册中心是否已经连接
    * @return true -> 已经连接
    */
  def isConnected:Boolean

  /**
    * 注册节点
    * @param node  待注册的节点
    * @return 注册结果，true表示注册成功
    */
  def registerNode(node:Node):Boolean

  /**
    * 注销节点
    * @param node 待注销的节点
    */
  def unRegisterNode(node:Node):Unit

  /**
    * 按照节点类型返回节点，
    * @param nodeType 节点类型
    * @return  该类型全部节点
    */
  def getNodesByType(nodeType:String):Array[Node]

  /**
    * 返回全部节点，包括节点类型，节点值，
    * @return 全部节点
    */
  def getAllNodes():Array[Node]
}