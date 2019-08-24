package oyster.domain


class User(var card: OysterCard) {

    def travel(start: OysterEnabledStation, end: OysterEnabledStation): Unit = {
        val usedCard = start.swipeIn(card)
        card = end.swipeOut(usedCard)
    }

    def topUpCard(amount: Int): Unit = {
        require(amount > 0) // TODO proper error handling missing

        card = card.copy(card.balance + amount)
    }

    def viewCardBalance(): Int = {
        card.balance
    }
}
