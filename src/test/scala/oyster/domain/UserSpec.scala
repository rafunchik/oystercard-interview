package oyster.domain

import org.scalatest._

class UserSpec extends FlatSpec with Matchers with UserWithOysterCard {

  "User" should "pay a correct fare for a single bus ride" in {
    val holborn = new BusStation(1, None)
    val earlsCourt = new BusStation(1, Some(2))
    val user = new User(OysterCard())
    user.topUpCard(InitialBalance)

    withLoadedOysterCard { user =>
      user.travel(holborn, earlsCourt)
      user.viewCardBalance() shouldBe InitialBalance - BusFares.BusFare
    }
  }

  "User" should "pay the minimal fare for a common zone tube ride" in {
    val eastPutney = new MetroStation(2, Some(3))
    val earlsCourt = new MetroStation(1, Some(2))

    withLoadedOysterCard { user =>
      user.travel(eastPutney, earlsCourt)
      user.viewCardBalance() shouldBe InitialBalance - MetroFares.AnyOneZoneNoZoneOneFare
    }
  }

  "User" should "pay the correct fare for a 2 zone tube ride in zone 1" in {
    val eastPutney = new MetroStation(2, Some(3))
    val holborn = new MetroStation(1, None)

    withLoadedOysterCard { user =>
      user.travel(eastPutney, holborn)
      user.viewCardBalance() shouldBe InitialBalance - MetroFares.AnyTwoZonesIncZoneOneFare
    }
  }

  "User" should "pay the correct fare for a 1 zone tube ride in zone 1" in {
    val holborn = new MetroStation(1, None)
    val earlsCourt = new MetroStation(1, Some(2))

    withLoadedOysterCard { user =>
      user.travel(holborn, earlsCourt)
      user.viewCardBalance() shouldBe InitialBalance - MetroFares.AnyOneZoneIncZoneOneFare
    }
  }

  "User" should "pay the max fare for a 3 zone tube ride" in {
    val wimbledon = new MetroStation(3, None)
    val holborn = new MetroStation(1, None)

    withLoadedOysterCard { user =>
      user.travel(wimbledon, holborn)
      user.viewCardBalance() shouldBe InitialBalance - MetroFares.MaximumFare
    }
  }

  "User" should "the correct fare for a 2 zone tube ride out of zone 1" in {
    val ealing = new MetroStation(3, Some(4))
    val fulham = new MetroStation(2, None)

    withLoadedOysterCard { user =>
      user.travel(fulham, ealing)
      user.viewCardBalance() shouldBe InitialBalance - MetroFares.AnyTwoZonesNoZoneOneFare
    }
  }

  "User" should "pay for a bus ticket only, if swiping in on a bus" in {
    // although would be more correct to either check that the destinations type coincide,
    // or add a check when swiping out for the starting station type

    val holborn = new BusStation(1, None)
    val wimbledon = new MetroStation(3, None)

    withLoadedOysterCard { user =>
      user.travel(holborn, wimbledon)
      user.viewCardBalance() shouldBe InitialBalance - BusFares.BusFare
    }
  }

  "User" should "pay a max fare when swiping in at a tube station and not swiping out" in {
    val hammesmith = new MetroStation(2, None)
    val holborn = new BusStation(1, None)

    withLoadedOysterCard { user =>
      user.travel(hammesmith, holborn)
      user.viewCardBalance() shouldBe InitialBalance - MetroFares.MaximumFare
    }
  }

  "User" should "be able to top up it's card" in {
    withLoadedOysterCard { user =>
      val amount = 1000

      user.topUpCard(amount)

      user.card.balance shouldBe InitialBalance + amount
    }
  }

  "User" should "not be able to top up an amount equal to 0" in {
    withLoadedOysterCard { user =>
      val amount = 0
      assertThrows[IllegalArgumentException] {
        user.topUpCard(amount)
      }
    }
  }

  "User" should "not be able to top up a negative amount" in {
    withLoadedOysterCard { user =>
      val amount = -10
      assertThrows[IllegalArgumentException] {
        user.topUpCard(amount)
      }
    }
  }

  "User" should "be able to view it's card balance" in {
    withLoadedOysterCard { user =>
        user.viewCardBalance() shouldBe 3000
    }
  }
}

trait UserWithOysterCard {
  val InitialBalance = 3000

  def withLoadedOysterCard(test: User => Any): Any = {
    val user = new User(OysterCard())
    user.topUpCard(InitialBalance)
    test(user)
  }
}