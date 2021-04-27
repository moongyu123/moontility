package com.moongyu123.moontility.common

import com.moongyu123.moontility.scheduler.{AntiAFKScheduler, ClipboardReceiverScheduler, ClipboardSenderScheduler}
import com.moongyu123.moontility.util.ClipboardUtil
import com.moongyu123.moontility.work.entity.UserInfo
import com.moongyu123.moontility.work.repository.UserInfoRepository
import javax.annotation.PostConstruct
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.{Autowired, Qualifier}
import org.springframework.stereotype.Component

object StartUp {

  //이전 clipboard 저장용
  private var beforeClipboard:String = _
  def getBeforeClipboard():String = beforeClipboard
  def setBeforeClipboard(input:String):Unit={
    beforeClipboard = input
  }

  //userInfo 저장용 pk
  private var myUserInfoId:Int = 0
  def getMyUserInfoId():Int = myUserInfoId
  def setMyUserInfoId(input:Int):Unit={
    myUserInfoId = input
  }

  private var myUserInfo:UserInfo = _
  def getMyUserInfo():UserInfo = myUserInfo
  def setMyUserInfo(input:UserInfo):Unit = {
    myUserInfo = input
  }

}

@Component
class StartUp{
  private val logger = LoggerFactory.getLogger(getClass)

  @Autowired
  @Qualifier("AntiAFKScheduler")
  val antiAFKScheduler:AntiAFKScheduler=null
  @Autowired
  @Qualifier("ClipboardSenderScheduler")
  val clipboardSenderScheduler:ClipboardSenderScheduler=null
  @Autowired
  @Qualifier("ClipboardReceiverScheduler")
  val clipboardReceiverScheduler:ClipboardReceiverScheduler=null

  @Autowired
  val userInfoRepository:UserInfoRepository=null

  @PostConstruct
  def init():Unit={

    System.setProperty("java.awt.headless", "false");	//HeadlessException 방지

    val clipboardText = ClipboardUtil.paste()
    logger.debug(s"startup process... \nclipboard text is \n $clipboardText \n")
    StartUp.setBeforeClipboard(clipboardText)

    val id = Option(userInfoRepository.findMaxId())
    StartUp.setMyUserInfoId(if(id.isDefined) id.get else 1)

    val userInfo = Option(userInfoRepository.findUserInfoById(StartUp.getMyUserInfoId))
    logger.debug("userInfo ====> " + userInfo)
    if(userInfo.isDefined){
      val userInfoData = userInfo.get
      if(userInfoData.isAntiafk_use) antiAFKScheduler.startScheduler()  //자리비움 방지 시작
      if(userInfoData.isClipboard_use){ //클립보드 전송 사용여부
        if(userInfoData.isClipboard_receive_mode){  //수신여부 확인
          clipboardReceiverScheduler.startScheduler()
        }else{
          clipboardSenderScheduler.startScheduler()
        }
      }
      StartUp.setMyUserInfo(userInfoData)
    }



  }
}
