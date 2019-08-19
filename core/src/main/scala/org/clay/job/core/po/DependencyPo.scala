package org.clay.job.core.po

import java.util.concurrent.TimeUnit
import org.clay.job.core.domain.UID


//依赖关系实体？
case class DependencyPo(uid: UID,    //描述依赖关系的数据条目id，
                        jobUid: UID,   //当前作业UID
                        dependJobUid: UID,  //依赖的作业UID
                        timeOffset: Long,   //当前作业与依赖作业的时间偏移量
                        timeOffsetUnit: TimeUnit,   //当前作业与依赖作业的时间偏移量单位
                        timeOffsetMilliSec: Long,   //偏移量的毫秒数，多少毫秒？
                        updateTime: java.sql.Timestamp = null) extends Po{   //更新时间  sql?
  override def toString: String = s"DependencyPo(uid=$uid,jobUid=$jobUid,dependJobUid=$dependJobUid,timeOffset=$timeOffset,timeOffsetUnit=$timeOffsetUnit," +
    s"timeOffsetMilliSec=$timeOffsetMilliSec)"
}