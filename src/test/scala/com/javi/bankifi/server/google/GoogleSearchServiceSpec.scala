package com.javi.bankifi.server.google

import com.javi.bankifi.server.google.GoogleSearchService._
import org.scalatest._

class GoogleSearchServiceSpec extends WordSpec with Matchers {

  val googleSearchService = new GoogleSearchService

  "GoogleSearchService" must {

    "return the second search result from Google for query Q" in {
        val Q = "bankifi"
        val expectedResult = QueryResult("BankiFi - Beyond Open | LinkedIn" ,
          "https://www.linkedin.com/company/bankifi",
      "Learn about working at BankiFi - Beyond Open. Join LinkedIn today for free. See who you know at BankiFi - Beyond Open, leverage your professional network, ...")

        val QueryResult(text,url,title) = googleSearchService.search(Q).unsafeRunSync()

      title should not be empty
      url should not be empty
      text should not be empty
      }
    }
}
