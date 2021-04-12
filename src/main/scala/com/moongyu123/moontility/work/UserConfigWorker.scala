package com.moongyu123.moontility.work

import java.io.{File, PrintWriter}

import com.moongyu123.moontility.work.snakeYmlVO.User

class UserConfigWorker {

  /**
    * user.toString을 yml형태로 작성한뒤 yml파일쓰기
    * (참고: snakeYaml)
    */
  def saveUserConfig(user:User): Boolean={
    val writer = new PrintWriter(new File("./src/main/resources/application-user.yml"))

    try{
      writer.write(user.toString)
      true
    }catch {
      case _:Exception => false
    }finally{
      writer.close()
    }
  }
}
