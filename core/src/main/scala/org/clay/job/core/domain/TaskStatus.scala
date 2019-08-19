package org.clay.job.core.domain


/**
  * 作业的状态
  * Created:刚创建。即刚被调度器生成此执行计划
  * Started:此调度计划被选中执行
  * Waiting:等待依赖任务执行完毕
  * Executing:正在执行中
  * Timeout:执行超时
  * Success:执行成功
  * Failed:执行失败
  * TimeoutStopped:执行失败并停止
  * MaxRetryStopped:达到最大重试次数并停止
  * Overloaded:?
  */
object TaskStatus extends Enumeration {

  type TaskStatus = Value

  val Created = Value("CREATED")
  val Started = Value("STARTED")
  val Waiting = Value("WAITING")
  val Executing = Value("EXECUTING")
  val Timeout = Value("TIMEOUT")
  val Success = Value("SUCCESS")
  val Failed = Value("FAILED")
  val TimeoutStopped = Value("TIMEOUT")
  val MaxRetryStopped = Value("MAX_RETRY")
  val Overloaded = Value("OVERLOADED")

  def apply(status:String): TaskStatus = TaskStatus.withName(status)

  implicit def enum2String(status: TaskStatus):String = status.toString
  implicit def Str2Enum(status: String):TaskStatus = TaskStatus(status)
}
