package org.clay.job.db.access

/**
  * DataAccess工厂类
  * 获取四个DataAccess实例
  */
trait DataAccessFactory {
  def init():Unit
  def getJobAccess:JobAccess   //job 代表作业
  def getDependencyAccess:DependencyAccess    //访问依赖关系
  def getScheduleAccess:ScheduleAccess   //访问调度计划
  def getTaskAccess:TaskAccess   //task 代表作业的一个实例
  def destroy():Unit
}