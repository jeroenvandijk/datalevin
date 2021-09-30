(ns datalevin.search
  "Fuzzy full-text search engine"
  (:require [clojure.string :as s]
            [datalevin.lmdb :as l]
            [datalevin.util :as u]
            [datalevin.constants :as c]
            [datalevin.bits :as b]
            [datalevin.constants :as c])
  (:import [datalevin.sm SymSpell Bigram]))

(if (u/graal?)
  (require 'datalevin.binding.graal)
  (require 'datalevin.binding.java))

(comment

  (def unigrams {"hello" 49 "world" 30})
  (def bigrams {(Bigram. "hello" "world") 30})

  (def sm (time (SymSpell. unigrams bigrams 2 7)))

  (.getDeletes sm)

  (.addBigrams sm {(Bigram. "hello" "world") 3})

  (.getBigramLexicon sm)

  (.addUnigrams sm {"hello" 3 "datalevin" 1})

  (.getUnigramLexicon sm)


  (.lookupCompound sm "hell" 2 false)

  (def env (l/open-kv "/tmp/search2"))

  (def lst (l/open-inverted-list env "i"))

  (l/put-list-items lst "a" [1 2 3 4] :string :long)
  (l/put-list-items lst "b" [5 6 7] :string :long)

  (l/list-count lst "a" :string)
  (l/list-count lst "b" :string)

  (l/del-list-items lst "a" :string)

  (l/in-list? lst "b" 7 :string :long)

  (l/get-list lst "a" :string :long)

  (l/close-kv env)

  )
