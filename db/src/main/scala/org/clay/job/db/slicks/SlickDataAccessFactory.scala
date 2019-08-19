package org.clay.job.db.slicks

import com.typesafe.config.Config
import org.clay.job.db.access._
import slick.jdbc.MySQLProfile.api._

class SlickDataAccessFactory(config:Config) extends DataAccessFactory{

  private final var database:Database = _
  private final var jobAccess:JobAccess = _
  private final var dependencyAccess:DependencyAccess = _
  private final var scheduleAccess:ScheduleAccess = _
  private final var taskAccess:TaskAccess = _

  override def getJobAccess: JobAccess = jobAccess

  override def getDependencyAccess: DependencyAccess = dependencyAccess

  override def getScheduleAccess: ScheduleAccess = scheduleAccess

  override def getTaskAccess: TaskAccess = taskAccess

  override def init(): Unit = {
    database = Database.forConfig("",config)
    jobAccess = new SlickJobAccess(database)    //Job增删改查相关
    dependencyAccess = new SlickDependencyAccess(database)  //检查依赖的方法在DependencyAccess里
    scheduleAccess = new SlickScheduleAccess(database) //调度相关
    taskAccess = new SlickTaskAccess(database)  //job相关
  }

  override def destroy(): Unit = {
    // 不要判断database是否null，不要问为什么
    database.close()
    database = null
    jobAccess = null
    dependencyAccess = null
    scheduleAccess = null
    taskAccess = null
  }
}
