package org.clay.job.core

/**
  * 所有种类消息的顶层
  */
trait Message  {

  final val at:Long = System.currentTimeMillis()  //消息发生的时间
}
