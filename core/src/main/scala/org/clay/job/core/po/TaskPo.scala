package org.clay.job.core.po

import org.clay.job.core.domain.TaskStatus.TaskStatus
import org.clay.job.core.domain.UID


case class TaskPo(uid: UID,
                  jobUid: UID,
                  scheduleUidd: UID,
                  retryId: Int,
                  taskTrackerNode: String,
                  state: TaskStatus,
                  eventTime: Long,
                  message: Option[String],
                  updateTime: java.sql.Timestamp = null) extends Po{
  override def toString: String = s"TaskPo(uid=$uid,jobUid=$jobUid,scheduleUidd=$scheduleUidd,retryId=$retryId,taskTrackerNode=$taskTrackerNode," +
    s"state=$state,eventTime=$eventTime,message=$message)"
}