package org.clay.job.db.proxy

import org.clay.job.db.proxy.command.DatabaseCommand




final case class DataAccessProxyException[T](source:DatabaseCommand[T],reason:Throwable) extends Throwable
