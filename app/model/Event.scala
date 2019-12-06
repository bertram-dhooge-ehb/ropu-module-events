package model
import java.util.Calendar
import java.text.SimpleDateFormat
import java.text.DateFormat

class Event(var date: , var title: String, var description: String) {
  val format = new SimpleDateFormat("dd-MM-yyyy")
  override def toString(): String =
    format.format(date) + "::" + title + "::" + description

  def getDate(): String = {
    return format.format(date.getTime())
  }
}
