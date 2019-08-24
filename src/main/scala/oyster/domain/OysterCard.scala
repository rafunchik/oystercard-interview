package oyster.domain

case class OysterCard(balance: Int = 0, lastVisitedStationOpt: Option[Station] = None)
