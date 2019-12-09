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

  def getDateFormatted(): String = {
    val format = new SimpleDateFormat("dd-MM-yyyy")
    return format.format(date)
  }
}
