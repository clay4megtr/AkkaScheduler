package org.clay.job.core.builder

import java.util.concurrent.TimeUnit

import org.clay.job.core.domain.{Dependency, UID}
import org.clay.job.core.tools.UIDGenerator

/**
  * Dependency创建器
  */
object DependencyBuilder {
  def apply(): DependencyBuilder = new DependencyBuilder()
}

class DependencyBuilder extends Builder[Dependency]{
  private var jobUid:UID = _
  private var dependJobId:UID = _
  private var timeOffset:Long = _
  private var timeOffsetUnit:TimeUnit = _
  def withJobUid(jobUid:UID):this.type = {
    this.jobUid = jobUid
    this
  }
  def withDependJobId(dependJobId:UID):this.type ={
    this.dependJobId = dependJobId
    this
  }
  def withTimeOffset(timeOffset:Long):this.type = {
    this.timeOffset = timeOffset
    this
  }
  def withTimeOffsetUnit(timeOffsetUnit:TimeUnit):this.type = {
    this.timeOffsetUnit = timeOffsetUnit
    this
  }
  override def build(): Dependency = Dependency(UIDGenerator.globalUIDGenerator.nextUID(),jobUid,dependJobId,timeOffset,timeOffsetUnit)
}