package org.clay.job.db.factory

import com.typesafe.config.{Config, ConfigFactory}
import org.clay.job.db.access.DataAccessFactory
import org.clay.job.db.slicks.SlickDataAccessFactory
import org.clay.job.utils.ExternalClassHelper._

import scala.util.{Failure, Success, Try}

/**
  * 根据传入的访问数据库的类型返回一个 数据访问工厂，现只支持slick
  */
object DatabaseFactory {

  def getDataAccessFactory:Try[DataAccessFactory] =
    getDataAccessFactory(ConfigFactory.load())

  def getDataAccessFactory(config:Config):Try[DataAccessFactory] =
    getDataAccessFactory(config.getStringOr("db.type","slick"),config)

  def getDataAccessFactory(databaseType:String, config:Config):Try[DataAccessFactory] = {
    databaseType.toLowerCase match {
      case "slick" =>
        Success(new SlickDataAccessFactory(config.getConfig(s"db.$databaseType")))
      case "quill" =>
        Failure(new UnsupportedOperationException("quill unsupported database driver! json field not supported ! "))
      case unknownDatabaseType =>
        Failure(new IllegalArgumentException(s"unknown database type $unknownDatabaseType,supported database type is [slick]"))
    }
  }

}
