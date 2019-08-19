package org.clay.job.db.access

import org.clay.job.core.domain.UID
import org.clay.job.core.po.TaskPo

import scala.concurrent.{ExecutionContext, Future}

trait TaskAccess extends DataAccess[UID,TaskPo]{
  def batchInsert(batch:Array[TaskPo])(implicit global: ExecutionContext):Future[Int]
}
