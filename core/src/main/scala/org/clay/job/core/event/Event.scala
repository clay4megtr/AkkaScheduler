package org.clay.job.core.event

import org.clay.job.core.Message

/**
  * 事件（event）的接口，标志发生了某件事
  */
trait Event extends Message   //Message中只有一个at字段，取的是当前时间

trait FailedEvent extends Event{
  def reason:String
}

