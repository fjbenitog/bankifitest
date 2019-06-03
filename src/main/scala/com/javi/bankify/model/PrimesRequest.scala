package com.javi.bankify.model

import io.circe._
import io.circe.generic.semiauto._

case class PrimesRequest(numbers: Long)

object PrimesRequest {
  implicit val primeRequestEncoder: Encoder[PrimesRequest] = deriveEncoder[PrimesRequest]

  implicit val PrimeRequestDecoder: Decoder[PrimesRequest] = deriveDecoder[PrimesRequest]
}
