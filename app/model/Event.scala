package model
import java.util.Calendar
import java.text.SimpleDateFormat
import java.text.DateFormat
import java.sql.Timestamp
import play.api.libs.json.Json
import play.api.libs.json._

class Event(
    var id: Int,
    var date: Timestamp,
    var title: String,
    var description: String
) {

  override def toString(): String =
    "id=" + id + "::" + "date=" + getDateFormatted() + "::" + "title=" + title + "::" + "description=" + description

  def toJson(): JsValue = {
    var result: Map[String, String] = Map()

    toString()
      .split("::")
      .map { t =>
        t.split("=")(0).toString() -> t.split("=")(1).toString()
      }
      .foreach { i =>
        result += i
      }

    return Json.toJson(result)
  }

  def getDateFormatted(): String = {
    val format = new SimpleDateFormat("dd-MM-yyyy")
    return format.format(date)
  }
}
