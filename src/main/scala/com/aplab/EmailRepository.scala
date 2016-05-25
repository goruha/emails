package com.aplab

import slick.driver.MySQLDriver.api._

/**
  * Created by goruha on 24.05.16.
  */

case class Email(id: Int, email: String, state: Int)

class EmailRepository(tag: Tag) extends Table[Email](tag, "emails") {
  def id = column[Int]("id", O.PrimaryKey, O.AutoInc)
  def email = column[String]("email")
  def state = column[Int]("state")

  def * = (id, email, state) <> (Email.tupled, Email.unapply)
}

object EmailRepository {

  def query: TableQuery[EmailRepository] = TableQuery[EmailRepository]

}
