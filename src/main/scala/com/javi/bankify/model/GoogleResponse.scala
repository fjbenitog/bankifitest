package com.javi.bankify.model

import io.circe._
import io.circe.generic.semiauto._

case class GoogleResponse(title: String, url: String, text: String)

object GoogleResponse {
  implicit val googleResponseEncoder: Encoder[GoogleResponse] = deriveEncoder[GoogleResponse]

  implicit val googleResponseDecoder: Decoder[GoogleResponse] = deriveDecoder[GoogleResponse]
}
