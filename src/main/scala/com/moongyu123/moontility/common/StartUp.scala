package com.moongyu123.moontility.common

import com.moongyu123.moontility.scheduler.{AntiAFKScheduler, ClipboardSenderScheduler}
import com.moongyu123.moontility.util.ClipboardUtil
import javax.annotation.PostConstruct
import org.springframework.beans.factory.annotation.{Autowired, Qualifier, Value}
import org.springframework.stereotype.Component

object StartUp {

  //기능2. 클립보드 sync
 /* private var mode:String = "send" //default:송신
  def setMode(input:String):Unit={
    mode = input
  }*/

  private var beforeClipboard:String = _
  def getBeforeClipboard():String = beforeClipboard
  def setBeforeClipboard(input:String):Unit={
    beforeClipboard = input
  }
/*
  private var adId:String =_
  def getAdId():String = adId
  def setAdId(adid:String):Unit={
    adId = adid
  }*/

}

@Component
class StartUp{

  @Autowired
  @Qualifier("AntiAFKScheduler")
  val antiAFKScheduler:AntiAFKScheduler=null
  @Autowired
  @Qualifier("ClipboardSenderScheduler")
  val clipboardSenderScheduler:ClipboardSenderScheduler=null

  @Value("${user.antiafk.use:false}")  //user가 설정한 yml에는 해당 키 자체가 없을수도 있으므로 default값 처리 필수
  val antiAfkUse:Boolean = false
  @Value("${user.clipboard.use:false}")
  val clipboardUse:Boolean = false

  @PostConstruct
  def init():Unit={

    System.setProperty("java.awt.headless", "false");	//HeadlessException 방지

    val clipboardText = ClipboardUtil.paste()
    println(s"startup process... \nclipboard text is \n $clipboardText \n")
    StartUp.setBeforeClipboard(clipboardText)


    if(antiAfkUse) antiAFKScheduler.startScheduler()
    if(clipboardUse) clipboardSenderScheduler.startScheduler()


  }
}
