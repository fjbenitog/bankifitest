package com.javi.bankify.model

import io.circe._
import io.circe.generic.extras.semiauto._

case class PrimesResponse(primes: List[Long])

object PrimesResponse {
  implicit val primeResponseEncoder: Encoder[PrimesResponse] =
    deriveUnwrappedEncoder[PrimesResponse]

  implicit val PrimeResponseDecoder: Decoder[PrimesResponse] =
    deriveUnwrappedDecoder[PrimesResponse]
}
