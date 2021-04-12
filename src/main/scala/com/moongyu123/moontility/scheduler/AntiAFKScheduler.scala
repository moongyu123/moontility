package com.moongyu123.moontility.scheduler

import java.awt.{MouseInfo, Robot}
import java.util.concurrent.TimeUnit

import com.moongyu123.moontility.common.StartUp
import com.moongyu123.moontility.util.ClipboardUtil
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.scheduling.Trigger
import org.springframework.scheduling.support.PeriodicTrigger
import org.springframework.stereotype.Component

/**
  * 자리비움 방지 Scheduler
  */
@Component
@Qualifier("AntiAFKScheduler")
class AntiAFKScheduler extends DynamicAbstractScheduler {
  private val logger = LoggerFactory.getLogger(getClass)

  //이전 마우스 포인터 위치
  var beforeX=0
  var beforeY=0

  override def runner(): Unit = {
    logger.debug("runner:AntiAFKScheduler")
    val beforeClipboard = StartUp.getBeforeClipboard()  //이전 클립보드
    val nowClipboard = ClipboardUtil.paste()  //현재 클립보드

    val pObj = MouseInfo.getPointerInfo.getLocation

    if(pObj.x == beforeX && pObj.y == beforeY){   //이전과 마우스 위치가 같다
      if(beforeClipboard == nowClipboard) {  //이전과 클립보드 내용이 같다
        wakeup()
      }
    }else{
      beforeX = pObj.x
      beforeY = pObj.y
    }

  }

  override def getTrigger(): Trigger = {
    new PeriodicTrigger(2, TimeUnit.MINUTES)
  }

  /**
    * 마우스 이동 기능
    */
  private def wakeup():Unit={

    val robot = new Robot()

    Thread.sleep(10)
    robot.mouseMove(MouseInfo.getPointerInfo.getLocation.x + 2, MouseInfo.getPointerInfo.getLocation.y + 2)
    Thread.sleep(10)
    robot.mouseMove(MouseInfo.getPointerInfo.getLocation.x - 2, MouseInfo.getPointerInfo.getLocation.y + 2)
    Thread.sleep(10)
    robot.mouseMove(MouseInfo.getPointerInfo.getLocation.x - 2, MouseInfo.getPointerInfo.getLocation.y - 2)
    Thread.sleep(10)
    robot.mouseMove(MouseInfo.getPointerInfo.getLocation.x + 2, MouseInfo.getPointerInfo.getLocation.y - 2)
  }


}
