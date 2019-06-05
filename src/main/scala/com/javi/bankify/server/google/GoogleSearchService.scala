package com.javi.bankify.server.google

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

import cats.effect.IO
import net.ruippeixotog.scalascraper.browser.{HtmlUnitBrowser, JsoupBrowser}
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.dsl.DSL.Parse._
import org.slf4j.LoggerFactory

case class QueryResult(title: String, url: String, text: String)

class GoogleSearchService {

  val logger = LoggerFactory.getLogger(this.getClass)
    val browser = HtmlUnitBrowser()

  def search(query: String): IO[QueryResult] = IO {
    logger.debug(s"Searching in Google for:$query")
    val doc = browser.get(s"https://www.google.com/search?q=${URLEncoder.encode(query,StandardCharsets.UTF_8.name())}")
    val element = (doc >> elementList(".r")).lift(2)
    element.map(e => QueryResult(e.text,e.select("a").head.attr("href"),""))
      .getOrElse(QueryResult("", "", ""))
  }
}
