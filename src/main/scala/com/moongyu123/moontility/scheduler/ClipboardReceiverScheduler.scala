package com.moongyu123.moontility.scheduler

import java.util.concurrent.TimeUnit

import com.moongyu123.moontility.common.StartUp
import com.moongyu123.moontility.util.{ClipboardUtil, HttpUtil}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.{Qualifier, Value}
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

  @Value("${user.clipboard.receive_method:GET}")
  val configClipboardReceiveMethod:String = ""

  @Value("${user.clipboard.receive_url:http}")
  val configClipboardReceiveUrl:String = ""

  @Value("${user.clipboard.send_data:{a:b, c:d}}")
  val configClipboardReceiveData:String = ""

  override def runner(): Unit = {
    var siteClipboardText = ""
    if(configClipboardReceiveMethod == "GET"){
      siteClipboardText = HttpUtil.sttpGet(configClipboardReceiveUrl)
    }else if(configClipboardReceiveMethod == "POST"){
      siteClipboardText = HttpUtil.sttpPost(configClipboardReceiveUrl, configClipboardReceiveData)
    }

    val beforeClipboardText = StartUp.getBeforeClipboard() // 이전 클립보드 내용
    val clipboardText = ClipboardUtil.paste()              // 현재 클립보드 내용

    if (beforeClipboardText == siteClipboardText) {
      return
    }
    if (siteClipboardText.nonEmpty && siteClipboardText != clipboardText) {
      ClipboardUtil.copy(siteClipboardText) //클립보드 변경
    }
  }

  override def getTrigger(): Trigger = {
    new PeriodicTrigger(1, TimeUnit.SECONDS)
  }



}
