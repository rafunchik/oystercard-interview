package oyster.domain

import org.scalatest.{FlatSpec, Matchers}

class OysterCardSpec extends FlatSpec with Matchers {

  "An OysterCard" should "have an initial 0 balance and no last visited station upon creation" in {
    val card = OysterCard()
    card.balance shouldBe 0
    card.lastVisitedStationOpt shouldBe None
  }
}
