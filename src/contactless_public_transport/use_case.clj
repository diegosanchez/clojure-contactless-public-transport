(ns contactless-public-transport.use_case
  (:require [clojure.spec.alpha :as s])
  (:gen-class))

;; pay-ride
;; ========

;; Step 0: Defining the function to be created
;; Defining the usecase function for user to pay a raid
;; The function is pay-ride

;; Step 0: Thinking about the arguments of the function
;;
;; - The 'card' itself
;; - The 'ride-cost'

;;  Step 1: Define the data required by the function

;; Issue 0: Detected
;;  Integer overflow arithmetic

;; Issue 1: Overdraft + trx
;;  The ride-cost should range from 0 - trx-min-value

(s/def ::overdraft
  pos-int?)

(def trx-min-value 500)
(def trx-max-value 5000)

(s/def ::ride-cost
  ;; pos-int? -501
  (s/int-in 0 trx-min-value))

(s/def ::trx
  ;; int?
  (s/int-in (- trx-min-value) trx-max-value))

(s/def ::transactions
  (s/coll-of ::trx))

(s/def ::card
  (s/keys
   :req-un [::overdraft ::transactions]))

(s/def ::status
  boolean?)

(s/def ::result
  (s/keys
   :req-un [::card ::status]))

;; Step 2: Write down function's signature

(s/fdef pay-ride
  :args (s/cat :card ::card
               :ride-cost ::ride-cost)
  :ret ::result)

;; (defn pay-raid
;;   "Returns true if the card's balance is equal or bigger than the ride-cost"
;;   [card ride-cost]
;;   false)

;; Step 3: Ilustrate function with some examples

;; (defn pay-raid
;;   "Returns true if the card's balance is equal or bigger than the ride-cost."
;;   Example 1 (only first use-case is included)
;;   - card.transactions: [100]
;;   - ride-cost: 10
;;   - result: {
;;       card: { :transactions [10]},
;;        status: true
;;     }
;;   [card ride-cost]
;;   false)
  
;; Step 4: Inventory

;; (defn pay-raid
;;   "Returns true if the card's balance is equal or bigger than the ride-cost."
;;   - card.transactions: [100]
;;   - ride-cost: 10
;;   - result: {
;;       card: { :transactions [10]},
;;        status: true
;;     }
;;   [card ride-cost]
;;   (... card ... ride-cost...))

;; Step 5.1: Code the solution

(defn create-trx
  [value]
  value)

(defn create-card
  [initial-balance overdraft]
  {:overdraft overdraft
   :transactions [(create-trx initial-balance)]})

(defn card-balance
  [card]
  (reduce + (:transactions card)))

(defn card-consume-credit
  [card amount]
  {:overdraft (:overdraft card)
   :transactions
   (concat [(- amount)] (:transactions card))})
  
(defn card-ride-affordable?
  [card ride-cost]
  (< ride-cost
              (+ (card-balance card)
                 (:overdraft card))))

(defn pay-ride
  "Returns true if the card's balance is equal or bigger than the ride-cost.
  Example 1 (only first use-case is included)
  - card.transactions: [100]
  - ride-cost: 10
  - result: {
     card: { :transactions [10]},
     status: true
  }
  Example 2 (only first use-case is included)
  - card.transactions: [100]
  - ride-cost: 110
  - result: {
     card: { :transactions []},
     status: false
  }
  Example 2 (only first use-case is included)
  - card.transactions: [100]
  - ride-cost: 110
  - result: {
     card: { :overdraft 10 :transactions []},
     status: true
  }
  "
  [card ride-cost]
  {:card (card-consume-credit card ride-cost),
   :status (card-ride-affordable? card ride-cost)})

;; Step 6.1: Write tests


;; See use_case_test.clj file

(comment
  (require '[clojure.spec.test.alpha :as stest])

  (first (stest/check `pay-ride)))
  

(comment
  (require '[clojure.spec.gen.alpha :as gen])
  (gen/sample (s/gen ::trx)))

  
(comment
  (pay-ride (create-card 100 10) 10))
