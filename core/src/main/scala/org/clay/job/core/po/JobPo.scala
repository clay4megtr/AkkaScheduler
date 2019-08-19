package org.clay.job.core.po

import java.util.concurrent.TimeUnit

import org.clay.job.core.domain.UID

/**
  * 和Job实体的关系是这个实体是映射和mysql的关系的实体，除了代表主键的UID没有，其他都是一一对应的
  * 它和Job的属性值可能有些不太一样，例如clusterName对应Job的cluster，groupName对应Job的group
  * 而且这里带 ? 的都是Job中不存在的；
  */
case class JobPo(uid: UID,    //作业UID值,唯一
                 name: String,  //作业名称，唯一
                 className: String,  //job对应的类名称
                 metaData: String,   // ? 作业执行参数 json
                 dataTimeOffset: Long,   // ?  偏移量
                 dataTimeOffsetUnit: TimeUnit,   // ？ 偏移量量的单位
                 startTime: Long,    //作业的开始时间，默认是当前时间
                 cron: String,       //job执行的cron表达式
                 priority: Int,      //作业的优先级，数值越大优先级越低，0是最大优先级。默认是最小优先级
                 parallel: Int,      //job的并发度。即同时执行的个数
                 retryTimes: Int,    //作业执行失败时重试的次数
                 workerNodes: Option[String],   //job可以执行的节点范围，如果不指定，则随机选择节点
                 clusterName: String, //job对应的集群名称
                 groupName: String,   //job对应的分组名称
                 timeout: Int,        //作业执行时超时时间
                 replaceIfExist: Boolean,   //如果作业已经存在，是否更新原来作业信息
                 lastGenerateTriggerTime: Option[Long],
                 schedulerNode: Option[String],     // 在哪个 scheduler 节点被调度的
                 scheduleFrequency: Option[Long],   // 调度的周期 (我们手动配置的)
                 lastScheduleTime: Option[Long],    // 最后一次被调度的时间
                 updateTime: java.sql.Timestamp = null) extends Po{
  override def toString: String = s"JobPo(uid=$uid,name=$name,class=$className,meta=$metaData,dataTimeOffset=$dataTimeOffset,dataTimeOffsetUnit=$dataTimeOffsetUnit," +
    s"startTime=$startTime,cron=$cron,priority=$priority,parallel=$parallel,retryTimes=$retryTimes,workerNodes=${workerNodes.mkString(",")}," +
    s"cluster=$clusterName,group=$groupName,timeout=$timeout,replaceIfExist=$replaceIfExist,lastGenerateTriggerTime=$lastGenerateTriggerTime," +
    s"schedulerNode=$schedulerNode,scheduleFrequency=$scheduleFrequency,lastScheduleTime=$lastScheduleTime)"
}