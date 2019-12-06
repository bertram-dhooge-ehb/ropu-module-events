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

  /* def getEvent(id: Int) = Action {
    var event = eventDAOInstance.getEventById(id)

    import AnyWriter.MyWriter.anyValWriter
    val a: Any = event

    Ok(Json.toJson(a))
  } */

  def deleteEvent(id: Int) = Action {
    eventDAOInstance.deleteEvent(id)
    Ok("Deleted from database")
  }

  /* def updateEvent(id: Int, date: String, title: String, description: String) {
    eventDAOInstance.updateEvent(id, date, title, description)
  } */

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
    eventDAOInstance.updateEvent(1, null, "UpdateTest", "Just testing update")

    Ok("Page loaded: " + testEvent)
  }
}
