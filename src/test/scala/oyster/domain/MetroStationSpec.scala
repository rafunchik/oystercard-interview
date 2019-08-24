package oyster.domain

import org.scalatest.{FlatSpec, Matchers}

class MetroStationSpec extends FlatSpec with Matchers {

  "Swipe in" should "preemptively charge the maximum fare" in {
    val station = new MetroStation(2, Some(3))
    val balance = 500
    val swipedCard = station.swipeIn(OysterCard(balance=balance))
    swipedCard.balance shouldBe balance - MetroFares.MaximumFare
  }

  "Swipe out" should "not charge, if there was no previous swipe in" in {
    val station = new MetroStation(1, None)
    val balance = 500
    val swipedCard = station.swipeOut(OysterCard(balance=balance))
    swipedCard.balance shouldBe balance
  }

  "Swipe out" should "will increase the balance of a forged card" in {
    //FIXME should not happen
    val station = new MetroStation(1, None)
    val balance = 500
    val card = OysterCard(balance = balance, lastVisitedStationOpt = Some(new MetroStation(1, None)))
    val swipedCard = station.swipeOut(card)
    swipedCard.balance shouldBe balance + MetroFares.MaximumFare - MetroFares.AnyOneZoneIncZoneOneFare
  }
}
