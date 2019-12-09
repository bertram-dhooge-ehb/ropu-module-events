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
      var result: Map[String, String] = Map()

      event
        .toString()
        .split("::")
        .map { t =>
          t.split("=")(0).toString() -> t.split("=")(1).toString()
        }
        .foreach { i =>
          result += i
        }

      Ok(Json.toJson(result))
    } else {
      Ok("Unable to find this id")
    }
  }

  def deleteEvent(id: Int) = Action {
    if (eventDAOInstance.deleteEvent(id)) Ok("Deleted from database")
    else Ok("Unable to delete from database, check id")
  }

  def updateEvent(data: String) = Action {
    if (data.contains("id=")) {
      /* Ok(Json.toJson(data.split("/").map { t =>
        t.split("=")(0) -> t.split("=")(1)
      })) */

      var result: Map[String, String] = Map()

      data
        .split("/")
        .map { t =>
          t.split("=")(0) -> t.split("=")(1)
        }
        .foreach { i =>
          result += i
        }

      eventDAOInstance.updateEvent(
        if (result.contains("id")) result("id") else null,
        if (result.contains("date")) result("date") else null,
        if (result.contains("title")) result("title") else null,
        if (result.contains("description")) result("description") else null
      )
      Ok(Json.toJson(result))
    } else {
      Ok("test failed")
    }

    /* var updatedEvent =
      eventDAOInstance.updateEvent(id, date, title, description)

    if (updatedEvent != null) {
      var eventData = updatedEvent.toString().split("::")

      var eventJson = Json.toJson(
        Map(eventData.map { t =>
          t.split("=")(0) -> t.split("=")(1)
        })
      )

      Ok(eventJson)
    } else {
      Ok("Unable to update this event")
    } */
  }

  /* def updateEvent(id: Int, date: String, title: String, description: String) {
    var updatedEvent =
      eventDAOInstance.updateEvent(id, date, title, description)

    if (updatedEvent != null) {
      var eventData = updatedEvent.toString().split("::")

      var eventJson = Json.toJson(
        Map(eventData.map { t =>
          t.split("=")(0) -> t.split("=")(1)
        })
      )

      Ok(eventJson)
    } else {
      Ok("Unable to update this event")
    }
  }
   */
  def index() = Action { implicit request: Request[AnyContent] =>
    eventDAOInstance.getAllEvents()

    var event =
      new Event(
        0,
        new Timestamp(ju.Calendar.getInstance().getTimeInMillis()),
        "testEvent",
        "This is a test event"
      )
    eventDAOInstance.createEvent(event)
    var testEvent = eventDAOInstance.getEventById(9)
    eventDAOInstance.deleteEvent(8)
    //eventDAOInstance.updateEvent(1, null, "UpdateTest", "Just testing update")

    Ok("Page loaded: " + testEvent)
  }
}
