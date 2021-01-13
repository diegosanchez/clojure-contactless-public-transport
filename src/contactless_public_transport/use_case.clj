(ns contactless-public-transport.use_case
  (:require [clojure.spec.alpha :as s])
  (:gen-class))

(defn create-trx
  [value]
  value)

(defn create-card
  [initial-balance]
  {:transactions [(create-trx initial-balance)]})


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

(s/def ::raid-cost
  pos-int?)

(s/def ::trx
  pos-int?)

(s/def ::transactions
  (s/coll-of ::trx))

(s/def ::card
  (s/keys
   :req [::transactions]))

(s/def ::result
  boolean?)

;; Step 2: Write down function's signature

(s/fdef pay-raid
  :args (s/cat ::card ::raid-cost)
  :ret ::result)

;; (defn pay-raid
;;   "Returns true if the card's balance is equal or bigger than the raid-cost"
;;   [card raid-cost]
;;   false)

;; Step 3: Ilustrate function with some examples

;; (defn pay-raid
;;   "Returns true if the card's balance is equal or bigger than the raid-cost.
;;   Example 1 (only first use-case is included)
;;   - card.transactions: [100]
;;   - raid-cost: 10
;;   - result: true
;;   "
;;   [card raid-cost]
;;   false)

;; Step 4: Inventory

;; (defn pay-raid
;;   "Returns true if the card's balance is equal or bigger than the raid-cost.
;;   Example 1 (only first use-case is included)
;;   - card.transactions: [100]
;;   - raid-cost: 10
;;   - result: true
;;   "
;;   [card raid-cost]
;;   (... card ... raid-cost...))

;; Step 5: Code the solution 

(defn card-balance
  [card]
  (reduce + (:transactions card)))

(defn pay-raid
  "Returns true if the card's balance is equal or bigger than the raid-cost.
  Example 1 (only first use-case is included)
  - card.transactions: [100]
  - raid-cost: 10
  - result: true
  Example 2 (only first use-case is included)
  - card.transactions: [100]
  - raid-cost: 110
  - result: false
  "
  [card raid-cost]
  (< raid-cost (card-balance card)))

;; Step 6: Write tests

;; See use_case_test.clj file
