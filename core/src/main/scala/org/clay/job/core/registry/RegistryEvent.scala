package org.clay.job.core.registry

/**
  * 注册中心事件
  *  注册一般指节点的注册，简单起见分为加入和离开两个事件
  */
object RegistryEvent extends Enumeration {
  type RegistryEvent = Value   //这里仅仅是为了将Enumration.Value的类型暴露出来给外界使用而已
  val JOIN = Value("JOIN")
  val LEAVE = Value("LEAVE")
}
