package org.clay.job.db.proxy.event

import org.clay.job.core.event.Event

/**
  * 数据库操作相关的事件
  * @tparam T 操作的数据库实体类型
  */
trait DatabaseEvent[T] extends Event{
  /**
    * 操作的数据行
    * @return 操作的数据行
    */
  def row:Option[T]

  /**
    * 原始的命令。由于是异步消息，所以需要保存操作数据库之前的命令明细
    * @return 原始的命令
    */
  def originCommand:AnyRef
}


object DatabaseEvent {


}