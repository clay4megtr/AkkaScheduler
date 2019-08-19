package org.clay.job.core.builder

trait Builder[T] {
  /**
    * 创建出对应的对象
    * @return 创建后的对象
    */
  def build():T
}
