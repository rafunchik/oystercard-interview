package oyster

import oyster.domain.{BusFares, BusStation, MetroFares, MetroStation, OysterCard, User}
import MetroFares._
import BusFares._

object FareCardSystem extends App {

  override def main(args: Array[String]): Unit = {
    val initialBalance: Int = 3000
    val earlsCourtTubeStation = new MetroStation(1, Some(2))
    val hammersmithTubeStation = new MetroStation(2, None)
    val earlsCourtBusStop = new BusStation(1, Some(2))
    val holbornTubeStation = new MetroStation(1, None)
    val chelseaBusStop = new BusStation(1, None)

    val user = new User(card = OysterCard())
    println(s"the user loads the card with ${initialBalance/100} pounds")
    user.topUpCard(initialBalance)
    println("the user travels by tube from Holborn to Earl's Court, hopefully not at peak time")
    user.travel(holbornTubeStation, earlsCourtTubeStation)
    println("the user travels by bus 328, from Earl's Court to Chelsea")
    user.travel(earlsCourtBusStop, chelseaBusStop)
    println("the user travels by tube from Earl's Court to Hammersmith")
    user.travel(earlsCourtTubeStation, hammersmithTubeStation)
    val finalBalance = initialBalance - AnyOneZoneIncZoneOneFare - BusFare - AnyOneZoneNoZoneOneFare
    println(s"the user's card balance should be: ${finalBalance/100}, and actually is: ${user.viewCardBalance()/100}")
  }

}
