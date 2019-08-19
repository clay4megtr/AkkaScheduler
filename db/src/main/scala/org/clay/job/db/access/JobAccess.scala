package org.clay.job.db.access

import org.clay.job.core.po.JobPo

import scala.concurrent.{ExecutionContext, Future}

/**
  * 与Job存储相关的接口
  */
trait JobAccess extends DataAccess[String,JobPo]{
  /**
    * 选择在一个周期内需要调度的作业
    * @param scheduleNode 调度器节点
    * @param scheduledFireTime 调度周期开始的时间
    * @param frequencyInSec 调度器周期，单位是秒
    * @param block 对结果以流的形式处理
    */
  def selectScheduleJob(scheduleNode:String,scheduledFireTime:Long,frequencyInSec:Long)(block: JobPo=>Unit)(implicit global: ExecutionContext):Unit

  /**
    * 寻找对应调度器负责调度的job
    * @param scheduleNode 调度器地址
    * @param block 对结果以流的形式处理
    */
  def selectJobsByScheduleNode(scheduleNode:String)(block: JobPo=>Unit)(implicit global: ExecutionContext):Unit

  /**
    * 批量插入JobPo
    * @param jobs 待插入的数据
    * @return 插入的个数
    */
  def batchInsert(jobs:Array[JobPo])(implicit global: ExecutionContext):Future[Int]

}
