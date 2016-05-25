package com.aplab


import java.lang.Exception

import org.xbill.DNS.{MXRecord, Type, Lookup}
import slick.backend.DatabaseConfig
import slick.driver.MySQLDriver
import slick.driver.MySQLDriver.api._
import scala.concurrent.duration._

import scala.concurrent.{Future, Await}
import scala.util.control.Exception
import scala.util.{Try, Failure, Success}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent._

import akka.io.Tcp


/**
  * Created by goruha on 17.05.16.
  */
object Emails {


  def main(args: Array[String]): Unit = {
    println("Hello, world!")

    val dbConfig: DatabaseConfig[MySQLDriver] = DatabaseConfig.forConfig("email.mysql.default")
    val db = dbConfig.db

    val emails = EmailRepository.query.filter(_.state === 0).take(100).result

    val res = db
      .run(emails)
      .flatMap( emails =>
        Future.sequence(
          emails.map( record =>
            Future {(record.id, record.email, EmailVerifier.verify(record.email))}
        ))
    )

    res onComplete {
      case Success(value) => value.filterNot(_._3).foreach(println(_))
      case Failure(t) => println("An error has occured: " + t.getMessage)
    }

    Await.result(res, 1500.minutes)
  }

}
