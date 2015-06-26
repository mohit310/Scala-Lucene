package com.mk.data

import play.api.libs.functional.syntax._
import play.api.libs.json._

/**
 * Created by mohit on 6/24/15.
 */
case class SampleData(name: String, age: Short)

object SampleData {
  implicit val personWrites = Json.writes[SampleData]
}

