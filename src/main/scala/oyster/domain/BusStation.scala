package oyster.domain

object BusFares {
  val BusFare = 180
}

class BusStation(stationZone: Short, anotherZoneOpt: Option[Short]) extends OysterEnabledStation(stationZone, anotherZoneOpt) {
  import BusFares.BusFare

  override val zone: Short = stationZone

  override val otherZoneOpt: Option[Short] = anotherZoneOpt

  override protected def calculateInBalance(card: OysterCard): Int = {
    card.balance - BusFare
  }

  override protected def calculateOutBalance(card: OysterCard): Int = {
    card.balance
  }
}
