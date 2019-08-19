package org.clay.job.core.actor

import akka.actor.{Actor, ActorLogging, ExtendedActorSystem}

import scala.concurrent.ExecutionContext

/**
  * 继承Actor和ActorLogging，并提供额外的通用字段
  */
trait SimpleActor extends Actor with ActorLogging{

  /**
    * Actor当前的Address
    */
  protected lazy final val selfAddress = context.system.asInstanceOf[ExtendedActorSystem].provider.getDefaultAddress

  /**
    * Actor当前的anchor。也就是带Address的path信息
    * path:         akka://ClusterSystem/user/frontend
    * selfAddress:  akka.tcp://ClusterSystem@127.0.0.1:2551
    * selfAnchor:   akka.tcp://ClusterSystem@127.0.0.1:2551/user/frontend
    */
  protected lazy final val selfAnchor = self.path.toStringWithAddress(selfAddress);


  protected final val config = context.system.settings.config


  protected implicit lazy val executor:ExecutionContext = context.dispatcher

  //留给子类实现的方法
  def userDefineEventReceive:Receive

  //统一未知消息处理
  protected def unknownEventReceive:Receive = {
    case unknownMessage:Any =>
      log.error(s"receive unknown message $unknownMessage, type is ${unknownMessage.getClass}")
  }

  override def receive: Receive = userDefineEventReceive orElse unknownEventReceive
}
