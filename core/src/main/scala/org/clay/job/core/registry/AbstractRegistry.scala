package org.clay.job.core.registry

import com.typesafe.config.Config


/**
  * 节点注册接口抽象类
  * 设定构造函数的形式
  * @param registryType  注册中心类型
  * @param config        注册中心配置文件
  */
abstract class AbstractRegistry(registryType:String,config:Config) extends Registry{

}