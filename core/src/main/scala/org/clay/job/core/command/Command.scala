package org.clay.job.core.command

import akka.actor.ActorRef
import org.clay.job.core.Message
import org.clay.job.core.domain._
import org.clay.job.core.domain.UID

//所有命令的抽象(命令也是一种消息)
trait Command extends Message

/**
  * 需要回复的命令，需要实现的接口
  */
trait Reply{

  /**
    * 每条命令都有一个消息的返回者，即命令被完整处理后应该把消息返回给谁(不一定是命令的发送者)
    * @return 命令执行后汇报的actor
    */
  def replyTo:ActorRef
}

/**
  * 需要回复的命令
  * DatabaseCommand 就实现了这个接口
  */
trait ReplyCommand extends Command with Reply{
}

//===============================================================================
//        Task相关Command
//Worker发送的命令，启动一个TaskTracker(一个TaskTracker对应一个jar包)
object TaskWorkerCommand{

  final case class StartTaskTracker(taskTrackerInfo:TaskTrackerInfo,replyTo:ActorRef) extends ReplyCommand
}

//TaskTracker发送的命令，启动一个TaskActor(一个TaskActor对应一个Task类)
object TaskTrackerCommand{
  final case class StartTaskActor(taskClassInfo:TaskClassInfo,claz:Class[_],replyTo:ActorRef) extends ReplyCommand
}

//TaskActor发送的命令，执行Task，
object TaskActorCommand{
  final case class RunTask(jobContext:JobContext,replyTo:ActorRef) extends ReplyCommand
}

//具体执行Task发送的命令，检查依赖
object TaskCommand{
  final case class CheckDependency(jobUid:UID,dataTime:Long,replyTo:ActorRef) extends ReplyCommand
}

//===============================================================================
//        Manager(JobTracker) 相关Command
//Manager发送的命令
object JobTrackerCommand{

  //提交Job
  final case class SubmitJob(job:Job,dependency:Array[Dependency],replyTo:ActorRef) extends ReplyCommand

  //调度Job
  final case class ScheduleJob(job:Job,replyTo:ActorRef) extends ReplyCommand
}

//===============================================================================
//        Scheduler 相关Command
//Scheduler 发送的命令
object JobSchedulerCommand{

  final case class ScheduleJobFreq(scheduleTime:Long,replyTo:ActorRef) extends ReplyCommand

  final case class ScheduleJob(job:Job,replyTo:ActorRef) extends ReplyCommand

  final case class StopScheduleJob(job:Job) extends Command
}

//===============================================================================
//        Client 相关Command
object JobClientCommand{

  final case class SubmitJob(job:Job,dependency:Array[Dependency]) extends Command

  final case class CancelJob(jobId:Long,force:Boolean) extends Command

  final case object Start extends Command

  final case object Stop extends Command
}


