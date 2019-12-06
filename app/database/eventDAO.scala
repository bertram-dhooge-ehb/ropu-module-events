package DAO

import model.Event
import scala.collection.mutable
import java.sql.PreparedStatement
import java.{util => ju}
import java.text.SimpleDateFormat
import java.sql.ResultSet
import java.sql.Timestamp

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
      System.out.println("No data");
      return null
    }

    resultSet.next()

    var title = resultSet.getString("title")
    var date = resultSet.getTimestamp("date")
    val format = new SimpleDateFormat("dd-MM-yyyy")
    var description = resultSet.getString("description")
    //ju.Calendar.getInstance(format.parse(dateString))

    var createdEvent = new Event(id, date, title, description)
    return createdEvent
  }

  def createEvent(event: Event) {
    var statement: PreparedStatement =
      connection.prepareStatement("INSERT INTO events  VALUES (NULL, ?, ?, ?) ")
    statement.setString(1, event.date.toString())
    statement.setString(2, event.title)
    statement.setString(3, event.description)

    statement.executeUpdate()
  }

  def deleteEvent(id: Int) {
    var statement: PreparedStatement =
      connection.prepareStatement("DELETE FROM events WHERE id = ?")
    statement.setString(1, id.toString())
    var resultSet = statement.executeUpdate()
  }

  def updateEvent(
      id: Int,
      date: Timestamp = null,
      title: String = null,
      description: String = null
  ) {
    if (!(date == null && title == null && description == null)) {
      var query = "UPDATE events SET "
      if (date != null) {
        query += "date = '" + date.toString()
      }
      if (title != null) {
        if (date != null) {
          query += "', "
        }
        query += "title = '" + title
      }
      if (description != null) {
        if (date != null || title != null) {
          query += "', "
        }
        query += "description = '" + description
      }
      query += "' WHERE id = " + id.toString()
      var statement: PreparedStatement =
        connection.prepareStatement(query)
      statement.executeUpdate()
    }
  }
}
