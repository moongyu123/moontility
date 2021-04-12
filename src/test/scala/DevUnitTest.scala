import java.awt.{MouseInfo, Robot}
import java.io.{File, PrintWriter}

import com.moongyu123.moontility.work.snakeYmlVO.{Antiafk, Clipboard, User}
import org.junit.Test
import org.yaml.snakeyaml.{DumperOptions, Yaml}

class DevUnitTest {

  @Test
  def moveMouse():Unit={
    val pObj = MouseInfo.getPointerInfo.getLocation

    val robot = new Robot()

    Thread.sleep(1000)
    robot.mouseMove(pObj.x + 2, pObj.y + 2)
    println("1")
    Thread.sleep(1000)
    robot.mouseMove(MouseInfo.getPointerInfo.getLocation.x + 2, MouseInfo.getPointerInfo.getLocation.y + 2)
    println("2")
    Thread.sleep(1000)
    robot.mouseMove(MouseInfo.getPointerInfo.getLocation.x + 2, MouseInfo.getPointerInfo.getLocation.y + 2)
    println("3")
    Thread.sleep(1000)
    robot.mouseMove(MouseInfo.getPointerInfo.getLocation.x + 2, MouseInfo.getPointerInfo.getLocation.y + 2)
    println("4")

  }

  @Test
  def snakeYml():Unit= {
    val user = new User()
    val antiafk = new Antiafk()
    antiafk.setUse(false)
    user.setAntiafk(antiafk)

    val clipboard = new Clipboard()
    clipboard.setUse(true)
    clipboard.setReceive_url("http://naver.com")
    clipboard.setSend_url("www.daum.net")
    user.setClipboard(clipboard)

    val writer = new PrintWriter(new File("./src/test/application-user.yml"))

    val yamlOptions = new DumperOptions
    yamlOptions.setIndent(2)
    yamlOptions.setPrettyFlow(true)
    yamlOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK)

    val yaml = new Yaml(yamlOptions)
    val copyUser = user
    val str = yaml.dump(copyUser)
    println(str)

    val lines = str.split(System.getProperty("line.separator"))
    val filteredLine = lines.filter(line => line.indexOf("!!") == -1).toList.mkString(System.getProperty("line.separator"))
    println(filteredLine)

//    yaml.dump(filteredLine, writer)

    println(user.toString)
//    yaml.dump(user.toString, writer)

    writer.write(user.toString)
    writer.close()
  }

  @Test
  def test()={
    val str1 = "StartUp"
    val str2 = "StartUp"

    println(!str1.equals(str2))
  }
}
