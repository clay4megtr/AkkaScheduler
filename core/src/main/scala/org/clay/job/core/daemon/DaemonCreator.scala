package org.clay.job.core.daemon

import akka.actor.{ActorRef, ActorSystem}
import com.typesafe.config.ConfigFactory
import org.clay.job.core.node.ClusterNodeProps
import scala.util.Try

//类似守护进程？
final case class Daemon(system:ActorSystem,actor:ActorRef)

object DaemonCreator {

  //默认端口号
  private val defaultPort = 0

  def createDaemon(nodeProps:ClusterNodeProps,port:Option[Int]):Try[Daemon] = Try{
    val config = ConfigFactory.parseString(s"akka.remote.netty.tcp.port=${port.getOrElse(defaultPort)}")
      .withFallback(ConfigFactory.load())
    val clusterName = config.getString("clusterNode.cluster-name")
    val system = ActorSystem(clusterName, config)
    val daemonActor = system.actorOf(nodeProps.props,nodeProps.daemonName)
    Daemon(system,daemonActor)
  }
}
