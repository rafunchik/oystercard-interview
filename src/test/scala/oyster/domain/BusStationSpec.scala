package oyster.domain

import org.scalatest.{FlatSpec, Matchers}

class BusStationSpec extends FlatSpec with Matchers {

  "Swipe in" should "charge the single ride bus fare" in {
    val station = new BusStation(1, None)
    val balance = 200
    val swipedCard = station.swipeIn(OysterCard(balance=balance))
    swipedCard.balance shouldBe balance - BusFares.BusFare
  }

  "Swipe out" should "not charge" in {
    val station = new BusStation(1, None)
    val balance = 200
    val swipedCard = station.swipeOut(OysterCard(balance=balance))
    swipedCard.balance shouldBe balance
  }

  "Swipe out" should "not charge, even after swiping in previously" in {
    val station = new BusStation(1, None)
    val balance = 200
    val card = OysterCard(balance = balance, lastVisitedStationOpt = Some(new BusStation(1, None)))
    val swipedCard = station.swipeOut(card)
    swipedCard.balance shouldBe balance
  }
}
