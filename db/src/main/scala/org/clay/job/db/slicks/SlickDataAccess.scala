package org.clay.job.db.slicks

import slick.jdbc.MySQLProfile.api._

/**
  * 为所有的DataAccess类提供构造函数的形式
  * 不提供对外的接口，接口统一由DataAccess暴露
  */
abstract class SlickDataAccess(database:Database) {

}
