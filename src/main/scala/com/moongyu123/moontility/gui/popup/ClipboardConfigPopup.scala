package com.moongyu123.moontility.gui.popup

import com.moongyu123.moontility.util.HttpUtil
import com.moongyu123.moontility.work.UserConfigWorker
import com.moongyu123.moontility.work.snakeYmlVO.{Antiafk, Clipboard, User}
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype

import scala.swing._
import scala.swing.event.ButtonClicked

@stereotype.Component
class ClipboardConfigPopup {
  private val logger = LoggerFactory.getLogger(getClass)

  @Value("${user.antiafk.use:false}")
  val configAntiAfkUse:Boolean = false

  @Value("${user.clipboard.use:false}")
  val configClipboardUse:Boolean = false

  @Value("${user.clipboard.isReceiveMode:false}")
  val configClipboardIsReceiveMode:Boolean = false

  @Value("${user.clipboard.receive_method:GET}")
  val configClipboardReceiveMethod:String = ""
  @Value("${user.clipboard.receive_url:http}")
  val configClipboardReceiveUrl:String = ""
  @Value("${user.clipboard.send_data:{a:b, c:d}}")
  val configClipboardReceiveData:String = ""

  @Value("${user.clipboard.send_method:GET}")
  val configClipboardSendMethod:String = ""
  @Value("${user.clipboard.send_url:http}")
  val configClipboardSendUrl:String = ""
  @Value("${user.clipboard.send_data:{a:b, c:d}}")
  val configClipboardSendData:String = ""
  @Value("${user.clipboard.send_data_key:content}")
  val configClipboardSendDataKey:String = ""

  def showConfig(parent:Component): Unit={
    val frame = new Frame()

    val infoLabel = new Label("")
    val antiAfkUseCombo = new ComboBox(List("사용", "사용안함"))
    val clipboardUseCombo = new ComboBox(List("사용", "사용안함"))
    val clipboardModeCombo = new ComboBox(List("수신", "송신"))

    val clipboardReceiveMethodCombo = new ComboBox(List("GET", "POST"))
    val clipboardReceiveUrlTextField = new TextField("http://", 30)
    val clipboardReceiveDataTextArea = new TextArea { rows = 4; lineWrap = true; wordWrap = true }
    val clipboardReceiveTestBtn = new Button("test")

    val clipboardSendMethodCombo = new ComboBox(List("GET", "POST"))
    val clipboardSendUrlTextField = new TextField("http://", 30)
    val clipboardSendDataTextArea = new TextArea { rows = 4; lineWrap = true; wordWrap = true }
    val clipboardSendKeyTextField = new TextField("", 10)
    val clipboardSendTestBtn = new Button("test")


    logger.debug(s"configAntiAfkUse : $configAntiAfkUse")
    logger.debug(s"configClipboardUse : $configClipboardUse")
    logger.debug(s"configClipboardIsReceiveMode : $configClipboardIsReceiveMode")
    logger.debug(s"configClipboardReceiveMethod : $configClipboardReceiveMethod")
    logger.debug(s"configClipboardReceiveUrl : $configClipboardReceiveUrl")
    logger.debug(s"configClipboardReceiveData : $configClipboardReceiveData")
    logger.debug(s"configClipboardSendMethod : $configClipboardSendMethod")
    logger.debug(s"configClipboardSendUrl : $configClipboardSendUrl")
    logger.debug(s"configClipboardSendData : $configClipboardSendData")
    logger.debug(s"configClipboardSendDataKey : $configClipboardSendDataKey")

    //config저장값 세팅
    if(configAntiAfkUse) antiAfkUseCombo.selection.item = "사용"
    else antiAfkUseCombo.selection.item = "사용안함"
    if(configClipboardUse) clipboardUseCombo.selection.item = "사용"
    else clipboardUseCombo.selection.item = "사용안함"
    if(configClipboardIsReceiveMode) clipboardModeCombo.selection.item = "수신"
    else clipboardModeCombo.selection.item = "송신"

    if(configClipboardReceiveMethod == "GET") clipboardReceiveMethodCombo.selection.item = "GET"
    else clipboardReceiveMethodCombo.selection.item="POST"
    clipboardReceiveUrlTextField.text = configClipboardReceiveUrl
    clipboardReceiveDataTextArea.text = configClipboardReceiveData

    if(configClipboardSendMethod == "GET") clipboardSendMethodCombo.selection.item = "GET"
    else clipboardSendMethodCombo.selection.item="POST"
    clipboardSendUrlTextField.text = configClipboardSendUrl
    clipboardSendDataTextArea.text = configClipboardSendData
    clipboardSendKeyTextField.text = configClipboardSendDataKey

    val saveConfig = new Button("저장")

    val configPopup = new BoxPanel(Orientation.Vertical){
      contents += new BoxPanel(Orientation.Horizontal) {
        contents += new Label("자리안비움 사용여부 : ")
        contents += Swing.HStrut(5)
        contents += antiAfkUseCombo
      }
      contents += Swing.VStrut(5)
      contents += new BoxPanel(Orientation.Horizontal) {
        contents += new Label("클립보드전송 사용여부 : ")
        contents += Swing.HStrut(5)
        contents += clipboardUseCombo
      }
      contents += Swing.VStrut(5)
      contents += new BoxPanel(Orientation.Horizontal) {
        contents += new Label("클립보드전송 수신/송신 선택 : ")
        contents += Swing.HStrut(5)
        contents += clipboardModeCombo
      }
      contents += Swing.VStrut(5)
      contents += new BoxPanel(Orientation.Horizontal) {
        contents += new Label("클립보드전송 수신 URL : ")
        contents += Swing.HStrut(5)
        contents += clipboardReceiveMethodCombo
        contents += clipboardReceiveUrlTextField
        contents += clipboardReceiveTestBtn
      }
      contents += Swing.VStrut(5)
      contents += new BoxPanel(Orientation.Horizontal) {
        contents += new Label("수신 post param")
        contents += clipboardReceiveDataTextArea
      }
      contents += Swing.VStrut(5)
      contents += new BoxPanel(Orientation.Horizontal) {
        contents += new Label("클립보드전송 송신 URL : ")
        contents += Swing.HStrut(5)
        contents += clipboardSendMethodCombo
        contents += clipboardSendUrlTextField
        contents += clipboardSendTestBtn

      }
      contents += Swing.VStrut(5)
      contents += new BoxPanel(Orientation.Horizontal) {
        contents += new Label("송신 post param")
        contents += clipboardSendDataTextArea
      }
      contents += Swing.VStrut(5)
      contents += new BoxPanel(Orientation.Horizontal) {
        contents += new Label("clipboard key")
        contents += clipboardSendKeyTextField
      }
      contents += new BoxPanel(Orientation.Horizontal) {
        contents += saveConfig
        contents += Swing.HGlue
        contents += infoLabel
        contents += Swing.HGlue
        contents += Button("Close") { frame.visible=false }
      }

      for (e <- contents) e.xLayoutAlignment = 0.0
      border = Swing.EmptyBorder(10, 10, 10, 10)
    }

    frame.listenTo(saveConfig)
    frame.listenTo(clipboardReceiveTestBtn)
    frame.listenTo(clipboardSendTestBtn)

    frame.reactions += {

      case clickEvent: ButtonClicked => {
        val clickButton = clickEvent.source
        val textOnButton = clickButton.text

        if(clickButton == saveConfig){
          val user = new User
          val antiafk = new Antiafk
          if(antiAfkUseCombo.selection.item == "사용" )antiafk.setUse(true)
          else antiafk.setUse(false)

          val clipboard = new Clipboard
          if(clipboardUseCombo.selection.item == "사용") clipboard.setUse(true)
          else clipboard.setUse(false)
          if(clipboardModeCombo.selection.item == "수신") clipboard.setReceiveMode(true)
          else clipboard.setReceiveMode(false)
          if(clipboardReceiveMethodCombo.selection.item == "GET") clipboard.setReceive_method("GET")
          else clipboard.setReceive_method("POST")
          if(clipboardSendMethodCombo.selection.item == "GET") clipboard.setSend_method("GET")
          else clipboard.setSend_method("POST")

          clipboard.setReceive_url(clipboardReceiveUrlTextField.text)
          clipboard.setReceive_data(clipboardReceiveDataTextArea.text)
          clipboard.setSend_url(clipboardSendUrlTextField.text)
          clipboard.setSend_data(clipboardSendDataTextArea.text)
          clipboard.setSend_data_key(clipboardSendKeyTextField.text)

          user.setAntiafk(antiafk)
          user.setClipboard(clipboard)

          logger.debug(s"save config data ==> ${user.toString}")

          val result = new UserConfigWorker().saveUserConfig(user)
          if(result){
            val res = Dialog.showConfirmation(configPopup,
              "저장되었습니다. 다음 프로그램부터 적용됩니다.(YES:종료, NO:종료하지 않음)",
              optionType=Dialog.Options.YesNo,
              title="save success")
            if (res == Dialog.Result.Ok) sys.exit(0)
            else frame.visible=false
          }else{
            Dialog.showMessage(configPopup, "저장에 실패하였습니다. 데이터를 확인해주세요.","save fail")
          }
        }//end save button

        if(clickButton == clipboardReceiveTestBtn){
          var resultStr = ""
          if(clipboardReceiveMethodCombo.selection.item == "GET"){
            resultStr = HttpUtil.sttpGet(clipboardReceiveUrlTextField.text)
          }else{
            resultStr = HttpUtil.sttpPost(clipboardReceiveUrlTextField.text, clipboardReceiveDataTextArea.text)
          }
          logger.debug(resultStr)
          Dialog.showMessage(configPopup, resultStr,"url로 가져온 결과입니다.")
        }

        if(clickButton == clipboardSendTestBtn){
          var resultStr = ""
          if(clipboardSendMethodCombo.selection.item == "GET"){
            resultStr = HttpUtil.sttpGet(clipboardSendUrlTextField.text)
          }else{
            resultStr = HttpUtil.sttpPost(clipboardSendUrlTextField.text, clipboardSendDataTextArea.text)
          }
          Dialog.showMessage(configPopup, "저장되었습니다.","send ok")
        }

      } //end button click

    } //end reaction

    frame.title="기본설정 저장"
    frame.contents = configPopup
    frame.centerOnScreen
    frame.visible = true

  }

}
