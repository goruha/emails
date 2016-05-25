package com.aplab

import org.scalatest._

class EmailVerifierSpec extends FlatSpec with Matchers {

  "Email verifier" should "verify valid email on domain" in {
    EmailVerifier.verify_smtp("alt3.gmail-smtp-in.l.google.com.", "goruha@gmail.com") should be (true)
  }

  "Email verifier" should "verify invalid email on domain" in {
    EmailVerifier.verify_smtp("alt3.gmail-smtp-in.l.google.com.", "goruha7123782asha271672@gmail.com") should be (false)
  }



  "Email verifier" should "verify valid email" in {
    EmailVerifier.verify("goruha@gmail.com") should be (true)
  }

  "Email verifier" should "verify invalid email" in {
    EmailVerifier.verify("goruha7123782asha271672@gmail.com") should be (false)
  }
}