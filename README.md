## Probe of concepts of "“The basic steps of a function design recipe” from HdP 

The book: “How to Design Programs: An Introduction to Programming and Computing (The MIT Press).” iBooks.

## The problem

A contactless card allows users to pay for a train or any other public transport ride. The card has a prepaid balance and can be used in two different modes:

- Overdraft: The card balance is X and the user has to pay X + D, the payment should be accomplished if the D <=10.
- Standard: The card balance is X and the user has to pay Y when Y < X, the payment should be accomplished.

 
Every transaction has to be record on the contactless card. 

### Use cases

- [ ]  The balance is 100 and the user has to pay 10. The payment is accomplished, the card has a new record and the balance is 90.
- [ ]  The balance is 100 and the user has to pay 110. The payment is not accomplished, the balance remains unchanged.
- [ ]  The balance is 100 and the user has to pay 110. The payment is accomplished, the card has a new record and the balance is -10.


## Observations

1. El enforcing automático en tiempo de compilición elimina algunos tipos posible de error (inconsistencia entre diseño e implementación del modelo). Verificación exhaustiva de parámetros inferidos pero no explicitados. Ej.: trx vs. ride-cost. 

1. Evidencia relaciones implicitas entre entidades (ride-cost, trx) evitando la propagación de erroes que solo se detectan en la puesta en producción/staging.

2. stest/check shouldn't be executed/exercised in CI, because of the randoness nature of the value generation.
