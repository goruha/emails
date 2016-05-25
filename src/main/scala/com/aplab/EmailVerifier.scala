package com.aplab

import java.io.PrintStream

import org.xbill.DNS.{MXRecord, Type, Lookup}
import sun.net.smtp.SmtpClient

import scala.io.BufferedSource
import scala.util.{Failure, Success, Try}
;

object EmailVerifier {

  def verify(email: String) = is_valid(email) & verify_server(email)

  def is_valid(email: String) = true

  def verify_smtp(host: String, email: String) = {
    var result = false
    val connection = new SmtpClient(host)
    try {
      connection.from("goruha@gmail.com")
      connection.to(email)
      result = true
    }
    catch {
      case e: Exception => result = false
    } finally {
      connection.closeServer()
    }

    result
  }

  def verify_server(email: String): Boolean = {
    Try(
      if (email.split('@').length == 2) {
        val domain  = email.split('@').last

        val x = new Lookup(domain, Type.MX)
          .run()
          .map(_.asInstanceOf[MXRecord])
          .sortBy(_.getPriority)
          .find(
            f => verify_smtp(f.getTarget.toString, email)
          ).map( f => true).getOrElse(false)
        x
      }
      else {
        false
      }
    )
    match {
      case Success(r) => r
      case Failure(ex) => false
    }
  }
}