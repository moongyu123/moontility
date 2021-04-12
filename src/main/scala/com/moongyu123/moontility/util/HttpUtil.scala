package com.moongyu123.moontility.util

import java.util.Objects

import com.softwaremill.sttp.{HttpURLConnectionBackend, asString, sttp, _}
import org.apache.http.client.entity.UrlEncodedFormEntity
import org.apache.http.client.methods.{CloseableHttpResponse, HttpPost}
import org.apache.http.impl.client.{CloseableHttpClient, HttpClients}
import org.apache.http.message.BasicNameValuePair
import org.apache.http.util.EntityUtils
import org.apache.http.{HttpStatus, NameValuePair}
import org.slf4j.LoggerFactory

object HttpUtil {
  private val logger = LoggerFactory.getLogger(getClass)

  def sttpGet(urlStr:String):String = {
    val req = sttp
      .get(uri"$urlStr")
      .response(asString)
    implicit val backend = HttpURLConnectionBackend()
    try {
      val res = req.send()
      res.unsafeBody

    } catch {
      case e: Exception => e.getMessage
    }
  }

  def strToMap(data:String):Map[String,String]={
    Objects.toString(data, "")
      .split(",")
      .map(_.split(":"))
      .map { case Array(k, v) => (k,v)}
      .toMap
  }

  def sttpPost(url:String, data:String): String ={

    val paramStr = Objects.toString(data, "")
    val isRawData = if(paramStr.startsWith("{")) true else false  //raw data 여부
//    logger.debug(s"org : $data")
//    logger.debug(s"paramStr : $paramStr")
//    logger.debug(s"isRawData : $isRawData")

    if(!isRawData){
      sendParamPost(url, strToMap(data))
    }else{
      sendRawPost(url, paramStr)
    }
  }

  private def sendRawPost(url:String, str:String)={
    val req = sttp
      .post(uri"$url")
      .body(str)
      .response(asString)

    implicit val backend = HttpURLConnectionBackend()
    try {
      val res = req.send()
      res.unsafeBody

    } catch {
      case _: Exception => ""
    }
  }

  def sendParamPost(url:String, param:Map[String,String]): String = {

    val httpClient: CloseableHttpClient = HttpClients.createDefault()

    val httpPost: HttpPost = new HttpPost(url)
    val nameValuePairs: java.util.List[NameValuePair] = new java.util.ArrayList[NameValuePair](1)
    for( key <- param.keySet){
      nameValuePairs.add(new BasicNameValuePair(key, param.getOrElse(key,"")))
    }
//    logger.debug(s"url : $url , nameValuePairs $nameValuePairs")
    httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "utf-8"))

    var response: CloseableHttpResponse = null
    var content = ""
    try {
      response = httpClient.execute(httpPost)
      val resCode: Int = response.getStatusLine.getStatusCode
//      println(s"load resCode : $resCode")

      if (HttpStatus.SC_OK == resCode) {
        content = EntityUtils.toString(response.getEntity)
      }
    } catch {
      case e: Exception =>
        println(e.getMessage)
    } finally {
      if (response != null) response.close()
      if (httpClient != null) httpClient.close()
    }
    content
  }

}
