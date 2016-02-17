package me.ciaranoconnor.models

import spray.json.DefaultJsonProtocol

case class MyData(data:String)

object MyData extends DefaultJsonProtocol {
  implicit val myDataFormat = jsonFormat1(MyData.apply)
}