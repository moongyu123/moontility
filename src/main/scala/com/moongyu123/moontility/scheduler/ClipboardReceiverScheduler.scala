package com.moongyu123.moontility.scheduler

import java.util.concurrent.TimeUnit

import com.moongyu123.moontility.common.StartUp
import com.moongyu123.moontility.util.{ClipboardUtil, HttpUtil}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.scheduling.Trigger
import org.springframework.scheduling.support.PeriodicTrigger
import org.springframework.stereotype.Component

/**
  * 클립보드 수신(Receive) Scheduler
  */
@Component
@Qualifier("ClipboardReceiverScheduler")
class ClipboardReceiverScheduler extends DynamicAbstractScheduler {
  private val logger = LoggerFactory.getLogger(getClass)

  override def runner(): Unit = {
    var siteClipboardText = ""
    val myInfo = StartUp.getMyUserInfo
    if(myInfo.getClipboard_receive_method == "GET"){
      siteClipboardText = HttpUtil.sttpGet(myInfo.getClipboard_receive_url)
    }else if(myInfo.getClipboard_receive_method == "POST"){
      siteClipboardText = HttpUtil.sttpPost(myInfo.getClipboard_receive_url, myInfo.getClipboard_receive_data)
    }

    val beforeClipboardText = StartUp.getBeforeClipboard() // 이전 클립보드 내용
    val clipboardText = ClipboardUtil.paste()              // 현재 클립보드 내용

    if (beforeClipboardText == siteClipboardText) {
      return
    }
    if (siteClipboardText.nonEmpty && siteClipboardText != clipboardText) {
      logger.debug(s"siteClipboardText : $siteClipboardText")
      ClipboardUtil.copy(siteClipboardText) //클립보드 변경
    }
  }

  override def getTrigger(): Trigger = {
    new PeriodicTrigger(1, TimeUnit.SECONDS)
  }



}
