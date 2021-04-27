package com.moongyu123.moontility.work

import java.io._

import com.moongyu123.moontility.work.entity.UserInfo
import com.moongyu123.moontility.work.repository.UserInfoRepository
import com.moongyu123.moontility.work.snakeYmlVO.User
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserConfigWorker {

  @Autowired
  val userInfoRepository:UserInfoRepository=null

  /**
    * user.toString을 yml형태로 작성한뒤 yml파일쓰기
    * (참고: snakeYaml)
    * 서버환경이나 로컬에서는 파일쓰기가 가능하지만 executable jar 환경에서는 쓰기불가능, 읽기는 ClassPathResource로 가능
    */
  @Deprecated
  def saveUserConfigByYml(user:User): Boolean={

//    import org.springframework.core.io.ClassPathResource
//    val resUserStream = new ClassPathResource("application-user.yml").getInputStream
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

  /**
    * entity로 db에 저장
    */
  def saveUserConfig(userInfo: UserInfo):Boolean={

    try{
      userInfoRepository.save(userInfo)
      true
    }catch {
      case _:Exception => false
    }
  }


}
