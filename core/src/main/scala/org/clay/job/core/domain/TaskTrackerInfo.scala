package org.clay.job.core.domain


/**
  * Task类信息
  * @param name 对应的主类名称
  * @param parallel 并发度
  * @param defaultTimeOut 默认超时时间
  */
final case class TaskClassInfo(name:String,parallel:Int,defaultTimeOut:Long){
  override def toString: String = s"TaskClassInfo(name=$name,parallel=$parallel,defaultTimeOut=$defaultTimeOut)"
}


/**
  * 给TaskTracker提供信息(一个jar包对应一个 TaskTracker),一个TaskTracker对应多个Task
  * @param cluster 集群名称
  * @param group 组名称
  * @param jarPath TaskTracker对应的jar包路径
  * @param classInfo 类信息
  */
final case class TaskTrackerInfo(cluster:String,group:String,jarPath:String,classInfo:Array[TaskClassInfo]){

  override def toString: String = s"TaskTrackerInfo(cluster=$cluster,group=$group,jarPath=$jarPath,classInfo=[${classInfo.mkString(",")}])"
}