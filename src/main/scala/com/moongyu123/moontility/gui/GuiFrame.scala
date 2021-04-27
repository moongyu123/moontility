package com.moongyu123.moontility.gui

import com.moongyu123.moontility.common.StartUp
import com.moongyu123.moontility.gui.popup.ClipboardConfigPopup
import com.moongyu123.moontility.scheduler.{AntiAFKScheduler, ClipboardReceiverScheduler, ClipboardSenderScheduler}
import com.moongyu123.moontility.work.entity.UserInfo
import com.moongyu123.moontility.work.repository.UserInfoRepository
import javax.imageio.ImageIO
import javax.swing.ImageIcon
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.{Autowired, Value}
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Service

import scala.swing._
import scala.swing.event.{ButtonClicked, SelectionChanged}

/*
scala swing 참고
- https://www.youtube.com/watch?v=KSG6EzxR9es
- https://otfried.org/scala/gui.html
- https://plus.cs.aalto.fi/o1/2019/w12/ch03/

Swing.HStrut : 양옆으로 띄우기
Swing.VStrut : 위아래로 띄우기
Swing.HGlue : 양옆 붙이기(?)
*/

@Service
class GuiFrame {
  private val logger = LoggerFactory.getLogger(getClass)

  @Value("${application.title}")
  protected val appTitle:String = ""
  @Value("${application.version}")
  protected val appVersion:String = ""

  /*@Value("${user.antiafk.use:false}")
  val configAntiAfkUse:Boolean = false
  @Value("${user.clipboard.use:false}")
  val configClipboardUse:Boolean = false
  @Value("${user.clipboard.isReceiveMode:false}")
  val configClipboardIsReceiveMode:Boolean = false*/

  @Autowired
  val antiAFKScheduler:AntiAFKScheduler=null

  @Autowired
  val clipboardSenderScheduler:ClipboardSenderScheduler=null

  @Autowired
  val clipboardReceiverScheduler:ClipboardReceiverScheduler=null

  @Autowired
  val clipboardConfigPopup:ClipboardConfigPopup=null

  @Autowired
  val userInfoRepository:UserInfoRepository=null

  def restrictHeight(s: Component) {
    s.maximumSize = new Dimension(Short.MaxValue, s.preferredSize.height)
  }

  def start(args: Array[String]): Unit = {

    val userInfoOpt = Option(userInfoRepository.findUserInfoById(StartUp.getMyUserInfoId()))
    var userInfo = new UserInfo()
    if(userInfoOpt.isDefined) userInfo = userInfoOpt.get

    //기능1. 자리비움영역
    val fn1BtnOnOff = new ToggleButton("ON")
    if(userInfo.isAntiafk_use) fn1BtnOnOff.selected = true
    else fn1BtnOnOff.text = "OFF"

    val fn1LabelInfo = new Label("1.자리안비움")
    val fn1Contents = new BoxPanel(Orientation.Horizontal) {
      //listener event
      listenTo(fn1BtnOnOff)

      reactions += {
        case clickEvent: ButtonClicked =>
          val clickButton = clickEvent.source
          val textOnButton = clickButton.text

          logger.debug(s"clickButton : $clickButton , textOnButton: $textOnButton" )
          //기능1. 자리비움 ONOFF
          if(clickButton == fn1BtnOnOff) {
            if(textOnButton == "ON"){   //자리비움 방지 종료
              clickButton.text = "OFF"
              antiAFKScheduler.stopScheduler()
            }
            else {  //자리비움방지 시작
              clickButton.text = "ON"
              antiAFKScheduler.startScheduler()
            }
          }

      } //end reactions
    }

    fn1Contents.contents += fn1LabelInfo
    fn1Contents.contents += fn1BtnOnOff


    //기능2. 클립보드 전송
    val fn2BtnOnOff = new ToggleButton("ON")
    if(userInfo.isClipboard_use) fn2BtnOnOff.selected = true
    else fn2BtnOnOff.text = "OFF"

    val fn2LabelInfo = new Label("2.VDI 클립보드전송")
    val fn2Contents = new BoxPanel(Orientation.Horizontal)
    fn2Contents.contents += fn2LabelInfo
    fn2Contents.contents += fn2BtnOnOff

    //기능2. 클립보드 전송 상세
    val fn2RadioStatus1 = new RadioButton("수신")
    val fn2RadioStatus2 = new RadioButton("송신")
    new ButtonGroup(fn2RadioStatus1, fn2RadioStatus2)
    fn2RadioStatus2.selected=true
    if(userInfo.isClipboard_receive_mode) fn2RadioStatus1.selected = true

    val fn2ContentDetail = new BoxPanel(Orientation.Horizontal){
      reactions += {
        case SelectionChanged(_) => println("combo change!!")

        case clickEvent: ButtonClicked => {
          val clickButton = clickEvent.source
          val textOnButton = clickButton.text

          println(s"clickButton : $clickButton , textOnButton: $textOnButton" )

          //기능2. 클립보드전송 ONOFF
          if(clickButton == fn2BtnOnOff) {
            if(textOnButton == "ON"){
              clickButton.text = "OFF"
              clipboardSenderScheduler.stopScheduler()
              clipboardReceiverScheduler.stopScheduler()
            }
            else {
              clickButton.text = "ON"
              if(fn2RadioStatus1.selected) clipboardReceiverScheduler.startScheduler()  //수신
              if(fn2RadioStatus2.selected) clipboardSenderScheduler.startScheduler()  //송신
            }
          }

          //기능2. 클립보드전송 송수신
          if(clickButton == fn2RadioStatus1 || clickButton == fn2RadioStatus2) {
            if(fn2BtnOnOff.text == "ON" && textOnButton == "송신"){
              println("송신!!!!")
              clipboardReceiverScheduler.stopScheduler()
              clipboardSenderScheduler.startScheduler()
            }
            else if(fn2BtnOnOff.text == "ON" && textOnButton == "수신"){
              println("수신!!!!")
              clipboardSenderScheduler.stopScheduler()
              clipboardReceiverScheduler.startScheduler()
            }
          }
        }
      }

      listenTo(fn2BtnOnOff)
      listenTo(fn2RadioStatus1)
      listenTo(fn2RadioStatus2)
    }
    fn2ContentDetail.contents += Swing.HStrut(1)
    fn2ContentDetail.contents += fn2RadioStatus1
    fn2ContentDetail.contents += fn2RadioStatus2
    fn2ContentDetail.contents += Swing.HStrut(20)

    val wholeLayout = new BoxPanel(Orientation.Vertical)
    wholeLayout.contents += fn1Contents
    wholeLayout.contents += Swing.VStrut(5)
    wholeLayout.contents += fn2Contents
    wholeLayout.contents += Swing.VStrut(5)
    wholeLayout.contents += fn2ContentDetail
    wholeLayout.contents += Swing.VStrut(5)


    // 메인프레임
    val mainFrame = new MainFrame{
      this.title = s"$appTitle $appVersion"

      //0. 메뉴
      menuBar = new MenuBar{
        contents += new Menu("File"){
          contents += new MenuItem(Action("Help"){
//            val helpImage = new ImageIcon("./src/main/resources/image/help.png" ) //file path로 넣으면 jar실행시 파일못찾음
            val helpImgStream = new ClassPathResource("image/help.png").getInputStream
            val icon = new ImageIcon(ImageIO.read(helpImgStream))

            Dialog.showMessage(message=null, icon=icon)
          })
          contents += new MenuItem(Action("Config"){
            clipboardConfigPopup.showConfig(this)
          })
          contents += new Separator
          contents += new MenuItem( Action("Exit"){
            sys.exit(0)
          })
        }
      }
      centerOnScreen  //모니터 중앙에 노출
    } //end MainFrame

    for (e <- wholeLayout.contents) e.xLayoutAlignment = 0.0
    mainFrame.contents = wholeLayout
//    mainFrame.size = new Dimension(400,300)
    mainFrame.visible = true

  }


}
