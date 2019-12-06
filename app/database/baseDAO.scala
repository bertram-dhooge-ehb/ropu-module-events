package DAO

import java.sql.DriverManager
import java.sql.Connection
import java.util.Calendar

class BaseDAO(
    var driver: String,
    var url: String,
    var username: String,
    var password: String
) {

  Class.forName(driver)
  var connection: Connection =
    DriverManager.getConnection(url, username, password)

}
