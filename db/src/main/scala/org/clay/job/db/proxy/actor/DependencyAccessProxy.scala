package org.clay.job.db.proxy.actor

import org.clay.job.core.actor.SimpleActor
import org.clay.job.core.command.JobTrackerCommand
import org.clay.job.core.domain.UID
import org.clay.job.core.po.DependencyPo
import org.clay.job.db.DataTables
import org.clay.job.db.access.DependencyAccess
import org.clay.job.db.proxy.DataAccessProxyException
import org.clay.job.db.proxy.command.DatabaseCommand
import org.clay.job.utils.ExternalClassHelper._

class DependencyAccessProxy(dependencyAccess:DependencyAccess) extends SimpleActor{
  /**
    * 用户自定义事件处理函数
    */
  override def userDefineEventReceive: Receive = {

    //插入消息，会先把所有依赖都删除，然后重新添加
    case cmd @ DatabaseCommand.Insert(row:Array[DependencyPo],replyTo,originCommand:JobTrackerCommand.SubmitJob) =>

      val insertDependency = dependencyAccess.deleteAllAndInsertMany(originCommand.job.uid,row) //删除之前的所有依赖，插入新的依赖
      insertDependency.mapAll( insertedNum =>
        DatabaseEvent.BatchInserted(insertedNum.getOrElse(0),row.headOption,originCommand)
        ,exception => DataAccessProxyException(cmd,exception))
        .pipeTo(replyTo)(sender())

    //查看任务的依赖是否满足
    case cmd @ DatabaseCommand.Select((DataTables.DEPENDENCY,jobId:UID,dataTime:Long),replyTo,originCommand) =>
      dependencyAccess.selectSucceedState(jobId,dataTime).mapAll(
        succeed => DatabaseEvent.Selected(Some((DataTables.DEPENDENCY,succeed)),originCommand),
        exception => DataAccessProxyException(cmd,exception) )
        .pipeTo(replyTo)(sender())
  }
}
