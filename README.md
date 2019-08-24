# Fare card system

The following app provides a simple model for a fare system, it can be run with _sbt_ in the following manner:

```bash
sbt run
```
 
which invokes FareCardSystem's main method and demonstrates the following scenario:

a user loading a card with £30, and taking the following trips, and then viewing the balance.
- Tube Holborn to Earl’s Court
- 328 bus from Earl’s Court to Chelsea
- Tube Earl’s court to Hammersmith

unit tests showing correct card charges can be run with:

```bash
sbt test
```

given the limited time, the following assumptions and simplifications were made:

- there is no check for negative balance, so the balance can decrease unlimitedly, a check would be easy to add to the 
  OysterEnabledStation.swipeIn method though. 
- no validation for scenarios like that both stations of a journey need to be of the same type.
- in a more realistic model, the fare calculation and updating of the card would be delegated to a payment system, which
  is inyected to the Station upon creation (using DI), favouring composition, rather than inheriting from a common 
  OysterEnabledStation class.
- at least one zone is provided for a Station, and the primary zone is closer to zone 1 than the optional secondary zone (not validated)
- assuming Chelsea is Zone 1 only.
- assuming a user boarding a bus will only get charged for a single bus ride, no matter the destination type.
- a tube swipe out should not charge, when there was no previous swipe in.
- as the state is kept on the card only (lastVisitedStation), the system assumes there was a swipe in if this is set, making it possible 
  to fool the system (see above on the possibility to use a separate payment system). 
- additional test coverage is needed, currently mostly the fares are tested.


PD: can re-implement in Python as well, if need be