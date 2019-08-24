package oyster.domain

trait Station {
  //TODO use enums instead of shorts
  val zone: Short
  val otherZoneOpt: Option[Short]
}
