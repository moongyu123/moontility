package com.moongyu123.moontility.scheduler

import org.springframework.scheduling.Trigger
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler

abstract class DynamicAbstractScheduler {
  private var scheduler:ThreadPoolTaskScheduler=_

  def stopScheduler():Unit={
    if(scheduler != null) scheduler.shutdown()
  }

  def startScheduler(): Unit ={
    scheduler = new ThreadPoolTaskScheduler()
    scheduler.initialize
    scheduler.schedule(getRunnable(), getTrigger())
  }

  private def getRunnable():Runnable = new Runnable() {
    override def run(): Unit = {
      runner()
    }
  }

  //abstract method
  def runner(): Unit
  def getTrigger(): Trigger

}
