(ns contactless-public-transport.use-case-test
  (:require [clojure.test :refer :all]
            [contactless-public-transport.use_case :refer :all]))

(deftest successful-ride
  (testing "Given a card with balance of 100 and user taking a train costing 10 can get it"
    (is (pay-raid (create-card 100) 10))))

(deftest failing-ride
  (testing "Given a card with balance of 100 and user taking a train costing 110 cannot get it"
    (is (not (pay-raid (create-card 100) 110)))))

