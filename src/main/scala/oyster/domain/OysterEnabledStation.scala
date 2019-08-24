package oyster.domain


abstract class OysterEnabledStation(zone: Short, otherZoneOpt: Option[Short]) extends Station {

  def topUp(card: OysterCard, amount: Int): OysterCard = card.copy(
    balance = card.balance + amount)

  def swipeIn(card: OysterCard): OysterCard = card.copy(
    balance = calculateInBalance(card),
    lastVisitedStationOpt = Some(this))

  def swipeOut(card: OysterCard): OysterCard = {
    card.lastVisitedStationOpt.fold(card) { lastVisitedStation =>
      card.copy(
        balance = calculateOutBalance(card),
        lastVisitedStationOpt = None)
    }
  }

  protected def calculateInBalance(card: OysterCard): Int

  protected def calculateOutBalance(card: OysterCard): Int
}
