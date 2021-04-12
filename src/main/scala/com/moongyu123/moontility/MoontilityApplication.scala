package com.moongyu123.moontility

import com.moongyu123.moontility.gui.GuiFrame
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class MoontilityApplication {

  @Autowired
  private val guiFrame:GuiFrame=null

  def run(args: Array[String]):Unit={
    System.setProperty("java.awt.headless", "false") //HeadlessException 방지

    guiFrame.start(args)

  }
}

object MoontilityApplication{
  private val logger = LoggerFactory.getLogger(getClass)

  def main(args: Array[String]): Unit = {
    logger.info("START APP")
    val context = SpringApplication.run(classOf[MoontilityApplication])
    val app = context.getBean(classOf[MoontilityApplication])
    app.run(args)

  }
}
