(ns contactless-public-transport.use-case-test
  (:require [clojure.test :refer :all]
            [contactless-public-transport.use_case :refer :all]))

(def a-card-with-balance-one-hundred
  (create-card 100 0))

(def a-card-with-balance-one-hundred-and-with-overdraft-of-ten
  (create-card 100 10))

(deftest successful-ride
  (testing "Given a card with balance of 100 and user taking a train costing 10 can get it"
    (is (:status
         (pay-ride a-card-with-balance-one-hundred
                   10)))
    (is (:status
         (pay-ride a-card-with-balance-one-hundred-and-with-overdraft-of-ten
                   109)))
    (is (:status
         (pay-ride
          (:card (pay-ride (create-card 100 0) 10))
          50)))))

(deftest failing-ride
  (testing "Given a card with balance of 100 and user taking a train costing 110 cannot get it"
    (is (not (:status
              (pay-ride a-card-with-balance-one-hundred
                        110))))
    (is (not (:status
         (pay-ride a-card-with-balance-one-hundred-and-with-overdraft-of-ten
                   120))))
    (is (not (:status
              (pay-ride
               (:card (pay-ride a-card-with-balance-one-hundred 10))
               95))))))
