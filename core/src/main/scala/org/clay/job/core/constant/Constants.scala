package org.clay.job.core.constant

/**
  * 常量字段
  */
object Constants {

  val ROLE_SCHEDULER_NAME = "JobScheduler"   //scheduler
  val ROLE_MANAGER_NAME = "JobTracker"       //manager
  val ROLE_WORKER_NAME = "TaskWorker"        //worker
  val ROLE_SEED_NAME = "seed"
  val DEFAULT_CLUSTER_NAME = "defaultCluster"
  val DEFAULT_GROUP_NAME = "defaultGroup"
  val CLUSTER_NAME_CONFIG_PATH = "clusterNode.cluster-name"  //集群名称
}