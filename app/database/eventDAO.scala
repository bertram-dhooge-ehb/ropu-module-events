package DAO

import model.Event
import scala.collection.mutable
import java.sql.PreparedStatement
import java.{util => ju}
import java.text.SimpleDateFormat
import java.sql.ResultSet
import java.sql.Timestamp
import java.time.LocalDate
import java.time.format.DateTimeFormatter

class EventDAO
    extends BaseDAO(
      "com.mysql.jdbc.Driver",
      "jdbc:mysql://dt5.ehb.be:3306/1920JAVAADV014",
      "1920JAVAADV014",
      "92478351"
    ) {
  def getAllEvents(): List[String] = {
    var statement: PreparedStatement =
      connection.prepareStatement("SELECT * FROM events")
    var resultSet = statement.executeQuery()
    var events: mutable.ListBuffer[String] = mutable.ListBuffer()

    while (resultSet.next()) {
      var id = resultSet.getString("id")
      var date = resultSet.getString("date")
      events += (id + "::" + date)
      println(events.length)
      println(id + "::" + date)
    }
    return events.toList
  }

  def getEventById(id: Int): Event = {
    var statement: PreparedStatement =
      connection.prepareStatement("SELECT * FROM events WHERE id = ?")
    statement.setString(1, id.toString())
    var resultSet = statement.executeQuery()

    if (!resultSet.isBeforeFirst()) {
      return null
    }

    resultSet.next()

    var title = resultSet.getString("title")
    var date = resultSet.getTimestamp("date")
    var description = resultSet.getString("description")

    var createdEvent = new Event(id, date, title, description)
    return createdEvent
  }

  def createEvent(event: Event): Event = {
    var statement: PreparedStatement =
      connection.prepareStatement("INSERT INTO events  VALUES (NULL, ?, ?, ?) ")
    statement.setString(1, event.date.toString())
    statement.setString(2, event.title)
    statement.setString(3, event.description)

    statement.executeUpdate()

    return event
  }

  def deleteEvent(id: Int): Boolean = {
    var statement: PreparedStatement =
      connection.prepareStatement("DELETE FROM events WHERE id = ?")
    statement.setString(1, id.toString())
    var resultSet = statement.executeUpdate()

    return resultSet > 0
  }

  def updateEvent(
      id: String,
      date: String = null,
      title: String = null,
      description: String = null
  ): Event = {

    var event = getEventById(id.toInt)

    if (event != null) {
      if (date != null && event.getDateFormatted() != date && LocalDate.parse(
            date,
            DateTimeFormatter.ofPattern("dd-MM-yyyy")
          ) != null) {
        event.date = Timestamp.valueOf(
          LocalDate
            .parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
            .atStartOfDay()
        )
      }
      if (title != null && event.title != title)
        event.title = title
      if (description != null && event.description != description) {
        event.description = description
      }

      var query =
        "UPDATE events SET date = ?, title = ?, description = ? WHERE id = ?"
      var statement: PreparedStatement = connection.prepareStatement(query)
      statement.setString(1, event.getDateFormatted())
      statement.setString(2, event.title)
      statement.setString(3, event.description)
      statement.setString(4, event.id.toString())
      statement.executeUpdate()
    }
    return event
  }
}
