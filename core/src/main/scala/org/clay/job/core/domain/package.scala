package org.clay.job.core

import org.clay.job.core.po.SchedulePo

package object domain {
  /**
    * 定义实体ID字段类型
    */
  type UID = String
  implicit def schedulePo2Schedule(schedulePo: SchedulePo):Schedule = Schedule(schedulePo.uid,schedulePo.jobUid,schedulePo.priority,schedulePo.retryTimes,
    schedulePo.dispatched,schedulePo.triggerTime,schedulePo.scheduleNode,schedulePo.scheduleTime)

}
