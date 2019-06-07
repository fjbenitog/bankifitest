package com.javi.bankifi.server.google

import java.net.URLEncoder
import java.nio.charset.StandardCharsets

import cats._
import cats.implicits._
import cats.effect._
import net.ruippeixotog.scalascraper.browser._
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.dsl.DSL.Extract._
import net.ruippeixotog.scalascraper.model._
import org.slf4j.LoggerFactory

object GoogleSearchService {

  case class QueryResult(title: String, url: String, text: String)

  implicit val showResult: Show[QueryResult] =
    Show.show(result => s"""QueryResult("${result.title}","${result.url}","${result.text}")""")

  implicit val showResults: Show[List[QueryResult]] =
    Show.show(results => s"{\n${results.map(_.show).reduce(_ + ",\n" + _)}\n}")

}

class GoogleSearchService {

  import GoogleSearchService._

  val googleQueryUrl = "https://www.google.com/search?q="

  val logger  = LoggerFactory.getLogger(this.getClass)
  val browser = HtmlUnitBrowser()

  def search(query: String): IO[QueryResult] = IO {

    logger.debug(s"Searching in Google for:$query ...")
    val doc: Document =
      browser.get(s"$googleQueryUrl${URLEncoder.encode(query, StandardCharsets.UTF_8.name())}")
    logger.debug(s"Results in Google for:$query ...")

    def toQueryResults(doc: Document) =
      for {
        element <- doc >> elementList(".rc")
        r       <- element.select(".r")
        title   <- r.select("h3")
        url     <- r.select("cite")
        s       <- element.select(".s")
      } yield QueryResult(title.text, url.text, s.text)

    def selectSecondResult(results: List[QueryResult]) =
      results
        .lift(2)
        .getOrElse(QueryResult("", "", ""))

    val results = toQueryResults(doc)

    logger.debug(s"All Elements found for query:$query - \n${results.show}")

    selectSecondResult(results)
  }
}
