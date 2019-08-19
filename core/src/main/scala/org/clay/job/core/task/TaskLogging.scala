package org.clay.job.core.task

import org.slf4j.{Logger, LoggerFactory}

/**
  * 任务的日志接口
  */
trait TaskLogging {

  this:Task => // 要求TaskLogging在实例化时或定义TaskLogging的子类时，必须混入指定的Task类型

  private var _log: Logger = _

  def log:Logger = {
    if(_log eq null)
      _log = LoggerFactory.getLogger(this.getClass)
    _log
  }
}