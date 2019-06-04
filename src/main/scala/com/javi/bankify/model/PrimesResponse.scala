package com.javi.bankify.model

import io.circe._
import io.circe.generic.semiauto._

case class PrimesResponse(algorithmName: String, primes: List[Long])

object PrimesResponse {
  implicit val primeResponseEncoder: Encoder[PrimesResponse] =
    deriveEncoder[PrimesResponse]

  implicit val PrimeResponseDecoder: Decoder[PrimesResponse] =
    deriveDecoder[PrimesResponse]
}
