(ns contactless-public-transport.card
  (:require [clojure.spec.alpha :as s]))

(s/def ::amount (s/and int? #(> % 0)))
(s/def ::trx (s/keys :req [::amount]))

(defn trx
  [initial-balance]
  {:amount initial-balance})

(defn card-with-balance
  [initial-balance]
  {:transactions
   [(trx initial-balance)]})
