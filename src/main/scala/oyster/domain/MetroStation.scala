package oyster.domain

object MetroFares {
  val MaximumFare = 320
  val AnyOneZoneNoZoneOneFare = 200
  val AnyOneZoneIncZoneOneFare = 250
  val AnyTwoZonesNoZoneOneFare = 225
  val AnyTwoZonesIncZoneOneFare = 300
}

class MetroStation(stationZone: Short, anotherZoneOpt: Option[Short]) extends OysterEnabledStation(stationZone, anotherZoneOpt) {
  import MetroFares._

  override val zone: Short = stationZone

  override val otherZoneOpt: Option[Short] = anotherZoneOpt

  override protected def calculateInBalance(card: OysterCard): Int = {
    card.balance - MaximumFare
  }

  override protected def calculateOutBalance(card: OysterCard): Int = {
    card.balance + MaximumFare - fare(card)
  }

  private def fare(card: OysterCard): Int = {
    card.lastVisitedStationOpt match {
      case Some(station) =>
        val zoneOne = inZoneOne(station)
        val zones = journeyNumberOfZones(station)
        zones match {
          case 0 => if(zoneOne) AnyOneZoneIncZoneOneFare else AnyOneZoneNoZoneOneFare
          case 1 => if(zoneOne) AnyTwoZonesIncZoneOneFare else AnyTwoZonesNoZoneOneFare
          case _ => MaximumFare
        }
      case None          => MaximumFare
    }
  }

  private def inZoneOne(destination: Station): Boolean = {
    (destination.zone == 1 && destination.otherZoneOpt.isEmpty) || (zone == 1 && anotherZoneOpt.isEmpty)
  }

  private def journeyNumberOfZones(destination: Station): Int = {
    //TODO simplify
    if (destination.zone - zone > 0) {
      math.min(
        math.abs(destination.zone - zone),
        math.abs(destination.zone - anotherZoneOpt.getOrElse(zone)))
    }
    else {
      math.min(
        math.abs(destination.otherZoneOpt.getOrElse(destination.zone) - zone),
        math.abs(destination.zone - anotherZoneOpt.getOrElse(zone)))
    }
  }
}
