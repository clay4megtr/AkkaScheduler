package org.clay.job.core.po

import org.clay.job.core.domain.UID


case class SchedulePo(uid: UID,
                      jobUid: UID,
                      priority: Int,
                      retryTimes: Int,
                      dispatched: Boolean,
                      triggerTime: Long,
                      scheduleNode: String,
                      scheduleTime: Long,
                      succeed: Boolean,
                      dataTime: Long, //计算方式: 从下次触发的时间开始，往后偏移(根据偏移量和偏移量单位)，返回偏移后的时间，这是干嘛用的呢？
                      updateTime: java.sql.Timestamp = null) extends Po{
  override def toString: String = s"Schedule(uid=$uid,jobUid=$jobUid,priority=$priority,retryTimes=$retryTimes,dispatched=$dispatched" +
    s",triggerTime=$triggerTime,scheduleNode=$scheduleNode,scheduleTime=$scheduleTime)"
}
