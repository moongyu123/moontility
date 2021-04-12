package com.moongyu123.moontility.util

import java.awt.Toolkit
import java.awt.datatransfer.{DataFlavor, StringSelection}

object ClipboardUtil {

  /**
    * 클립보드에 저장
    */
  def copy(text:String):Unit={
    val clipboard = Toolkit.getDefaultToolkit.getSystemClipboard
    clipboard.setContents(new StringSelection(text),null)
  }

  /**
    * 클립보드내용 가져오기
    */
  def paste():String={
    val clipboard = Toolkit.getDefaultToolkit.getSystemClipboard
    val flavor = DataFlavor.stringFlavor

    if(clipboard.isDataFlavorAvailable(flavor)){
      clipboard.getData(flavor).asInstanceOf[String]
    }else{
      ""
    }
  }
}
