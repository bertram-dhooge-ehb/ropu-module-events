package controllers

import javax.inject._
import play.api._
import play.api.mvc._
import DAO._
import model._
import java.{util => ju}

/**
  * This controller creates an `Action` to handle HTTP requests to the
  * application's home page.
  */
@Singleton
class EventsController @Inject()(cc: ControllerComponents)
    extends AbstractController(cc) {

  /**
    * Create an Action to render an HTML page.
    *
    * The configuration in the `routes` file means that this method
    * will be called when the application receives a `GET` request with
    * a path of `/`.
    */
  def index() = Action { implicit request: Request[AnyContent] =>
    var eventDAOInstance: DAO.EventDAO = new DAO.EventDAO()
    eventDAOInstance.getAllEvents()

    var event =
      new Event(ju.Calendar.getInstance(), "testEvent", "This is a test event")
    eventDAOInstance.createEvent(event)
    Ok(views.html.index())
  }
}
