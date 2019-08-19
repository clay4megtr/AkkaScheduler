package org.clay.job.core.domain

import org.clay.job.core.domain.UID

/**
  * 实体trait
  * 限制必须拥有id字段且可序列化 优秀
  */
trait Entity extends Serializable{
  def uid:UID
}
