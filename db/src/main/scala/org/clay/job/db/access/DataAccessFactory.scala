package org.clay.job.db.access

/**
  * DataAccess工厂类
  * 获取四个DataAccess实例
  */
trait DataAccessFactory {

  def init():Unit
  def getJobAccess():JobAccess    //Job代表作业
}
