package org.clay.job.core.po

import org.clay.job.core.domain.Entity

/**
  * 数据库表实例父类
  */
trait Po extends Entity{
  def updateTime: java.sql.Timestamp
}
