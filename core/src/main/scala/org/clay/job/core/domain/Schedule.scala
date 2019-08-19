package org.clay.job.core.domain

import org.clay.job.core.domain.UID

/**
  * 调度计划
  * @param uid 计划ID
  * @param jobUid 作业ID
  * @param priority 优先级
  * @param retryTimes 重试次数
  * @param dispatched 是否被调度
  * @param triggerTime 触发时间long
  * @param scheduleNode 调度节点
  * @param scheduleTime 调度时间long
  * @param updateTime 更新时间
  */
case class Schedule(
                     uid:UID,
                     jobUid:UID,
                     priority:Int,
                     retryTimes:Int,
                     dispatched:Boolean,
                     triggerTime:Long,
                     scheduleNode:String,
                     scheduleTime:Long,
                     updateTime:java.sql.Timestamp = null) extends Entity {

  override def toString: String = s"Schedule(uid=$uid,jobUid=$jobUid,priority=$priority,retryTimes=$retryTimes,dispatched=$dispatched" +
    s",triggerTime=$triggerTime,scheduleNode=$scheduleNode,scheduleTime=$scheduleTime})"
}