package com.mk

import com.mk.data.SampleData
import play.api.libs.json.{Writes, JsValue, Json}


/**
 * Created by mohit on 6/24/15.
 */
object ObjectToJsonConverter {

  def convert(x: SampleData) = {
    Json.toJson(x).toString()
  }
}
