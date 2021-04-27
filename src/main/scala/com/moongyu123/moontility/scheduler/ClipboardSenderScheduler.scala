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
  * 클립보드 송신(Send) Scheduler
  */
@Component
@Qualifier("ClipboardSenderScheduler")
class ClipboardSenderScheduler extends DynamicAbstractScheduler {
  private val logger = LoggerFactory.getLogger(getClass)

  override def runner(): Unit = {
    val myInfo = StartUp.getMyUserInfo
    val beforeClipboardText = StartUp.getBeforeClipboard() //이전 클립보드 내용
    val clipboardText = ClipboardUtil.paste() //현재 클립보드 내용

    if (!beforeClipboardText.equals(clipboardText)) { //클립보드내용 변경시 원하는 사이트로 저장전송
      logger.debug(s"clipboard changed! [before:$beforeClipboardText \n to $clipboardText")
      StartUp.setBeforeClipboard(clipboardText)
      if(myInfo.getClipboard_send_method == "GET"){
        HttpUtil.sttpGet(myInfo.getClipboard_send_url)
      }else if(myInfo.getClipboard_send_method == "POST"){
        val map = HttpUtil.strToMap(myInfo.getClipboard_send_data) + (myInfo.getClipboard_send_data_key -> clipboardText)
        HttpUtil.sendParamPost(myInfo.getClipboard_send_url, map)
      }
    }
  }

  override def getTrigger(): Trigger = {
    new PeriodicTrigger(1, TimeUnit.SECONDS)
  }



}
