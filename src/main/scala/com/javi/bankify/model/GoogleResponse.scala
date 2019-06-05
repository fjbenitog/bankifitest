package com.javi.bankify.model

import io.circe._
import io.circe.generic.semiauto._

sealed trait GoogleResponse

object GoogleResponse {
  implicit val googleResponseEncoder: Encoder[GoogleResponse] = {
    case r: GoogleQueryResult  => deriveEncoder[GoogleQueryResult].apply(r)
    case f: GoogleQueryFailure => deriveEncoder[GoogleQueryFailure].apply(f)
  }

}

case class GoogleQueryResult(title: String, url: String, text: String) extends GoogleResponse

case class GoogleQueryFailure(message: String) extends GoogleResponse
