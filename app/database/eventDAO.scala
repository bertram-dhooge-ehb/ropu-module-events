package DAO

import model.Event
import scala.collection.mutable
import java.sql.PreparedStatement
import java.{util => ju}
import java.text.SimpleDateFormat

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

  def getEventById(id: String): Event = {
    var statement: PreparedStatement =
      connection.prepareStatement("SELECT * FROM events WHERE id = ?")
    statement.setString(1, id)
    var resultSet = statement.executeQuery()

    if (!resultSet.isBeforeFirst()) {
      System.out.println("No data");
      return null
    }

    var id = resultSet.getString("id")
    var dateString = resultSet.getString("date")
    var title = resultSet.getString("title")
    val format = new SimpleDateFormat("dd-MM-yyyy")
    var description = resultSet.getString("description")

    var date = ju.Calendar.getInstance(format.parse(dateString))

    var createdEvent = java.awt.Event()
  }

  def createEvent(event: Event) {
    var statement: PreparedStatement =
      connection.prepareStatement("INSERT INTO events  VALUES (NULL, ?, ?, ?) ")
    statement.setString(1, event.getDate())
    statement.setString(2, event.title)
    statement.setString(3, event.description)

    statement.executeUpdate()
  }
}

/**
  * A Scala JDBC connection example by Alvin Alexander,
  * https://alvinalexander.com
  */
/* object ScalaJdbcConnectSelect {

    def main(args: Array[String]) {
        // connect to the database named "mysql" on the localhost
        val driver =  "com.mysql.jdbc.Driver"
        val url = "jdbc:mysql://dt5.ehb.be:3306/1920JAVAADV014"
        val username = "1920JAVAADV014"
        val password = "92478351"

        // Make connection with db
        Class.forName(driver)
        var connection:Connection = DriverManager.getConnection(url, username, password)

        Array(java.awt.Event) events;

         // create the statement, and run the select query
        val statement = connection.createStatement()
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS events(date varchar(255))")
        statement.executeUpdate("CREATE TABLE IF NOT EXISTS users(id varchar(255))")
        /* val resultSet = statement.executeQuery("CREATE TABLE events(date varchar(255))") */
        /* while ( resultSet.next() ) {
            val host = resultSet.getString("host")
            val user = resultSet.getString("user")
            println("host, user = " + host + ", " + user)
        }  */

        var datum = Calendar.getInstance()
        println(datum)

        connection.close()
    }

    } */
