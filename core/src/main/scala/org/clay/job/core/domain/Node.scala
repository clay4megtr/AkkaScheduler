package org.clay.job.core.domain

/**
  * 注册的节点信息 [节点类型，节点值]
  */
final case class Node(nodeType:String,anchor:String) {
  override def toString: String = s"Node($nodeType,$anchor)"
}
