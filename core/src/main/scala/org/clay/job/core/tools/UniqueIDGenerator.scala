package org.clay.job.core.tools

import java.util.UUID
import org.clay.job.core.domain.UID

/**
  * UID生成器接口
  */
trait UniqueIDGenerator {
  def nextUID():UID
}

object UIDGenerator{
  val globalUIDGenerator:UniqueIDGenerator = new UIDGenerator
}

class UIDGenerator extends UniqueIDGenerator{
  override def nextUID(): UID = UUID.randomUUID().toString
}