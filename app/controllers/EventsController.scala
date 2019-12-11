package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import play.api.libs.json.Json
import play.api.libs.json._
import DAO._
import model._
import java.{util => ju}
import java.sql.Timestamp
import scala.collection.mutable
import java.time.LocalDateTime
import java.time.LocalDate
import java.time.format.DateTimeFormatter

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class EventsController @Inject()(cc: ControllerComponents)
    extends AbstractController(cc) {

  var eventDAOInstance: DAO.EventDAO = new DAO.EventDAO()

  /**
    * Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def getAllEvents() = Action {
    var events = eventDAOInstance.getAllEvents()

    var eventsJson = Json.toJson(
      Map("events" -> events.map { t =>
        Map("id" -> t.split("::")(0), "date" -> t.split("::")(1))
      })
    )
    Ok(eventsJson)
  }

  def getEvent(id: Int) = Action {
    var event = eventDAOInstance.getEventById(id)
    if (event != null) {
      Ok(event.toJson)
    } else {
      NotFound
    }
  }

  def deleteEvent(id: Int) = Action {
    if (eventDAOInstance.deleteEvent(id)) Ok("Deleted from database")
    else NotFound
  }

  def updateEvent() = Action(parse.json) { request =>
    val id = (request.body \ "id").asOpt[Int]

    if (id.isDefined) {
      val date = (request.body \ "date").asOpt[String]
      val title = (request.body \ "date").asOpt[String]
      val description = (request.body \ "date").asOpt[String]

      val event = eventDAOInstance.updateEvent(
        id.get.toString,
        if (date.isDefined) date.get else null,
        if (title.isDefined) title.get else null,
        if (description.isDefined) description.get else null
      )

      Ok(id.get.toString)
    } else NotFound
  }

  def createEvent() = Action(parse.json) { request =>
    val date = (request.body \ "date").asOpt[String]
    val title = (request.body \ "date").asOpt[String]
    val description = (request.body \ "date").asOpt[String]

    if (date.isDefined && LocalDate.parse(
          date.get,
          DateTimeFormatter.ofPattern("dd-MM-yyyy")
        ) != null) {
      val event = new Event(
        0,
        Timestamp.valueOf(
          LocalDate
            .parse(date.get, DateTimeFormatter.ofPattern("dd-MM-yyyy"))
            .atStartOfDay()
        ),
        if (title.isDefined) title.get else null,
        if (description.isDefined) description.get else null
      )
      Ok(eventDAOInstance.createEvent(event).toJson)
    } else BadRequest("Can't have an event without a date")

  }
}
