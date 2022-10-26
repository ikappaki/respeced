(ns tasks
  (:require [babashka.deps :as deps]
            [babashka.fs :as fs]
            [babashka.process :as p]))

(defn clj-test
  [alias-clojure-version]
  (println "=== Running clj-tests" alias-clojure-version)
  (let [alias (format "-A:test%s:clj-tests" alias-clojure-version)]
    (-> (deps/clojure [alias])
        p/check)))

(defn cljs-test
  [alias-clojure-version]
  (let [alias (format "-A:test%s:compile-node-tests" alias-clojure-version)]
    (println "\n=== Compiling node tests" alias-clojure-version)
    (println :alias alias)
    (-> (deps/clojure [alias])
        p/check)
    (println "\n=== Running node tests")
    (p/shell "node out/main.js")))

(defn plk-test
  [alias-clojure-version]
  (println "\n=== Running plk-tests" alias-clojure-version)
  (if (fs/windows?)
    (println "\n=== [SKIPPING] plk is not available on MS-Windows!")

    (let [alias (format "-A:test%s:plk-tests" alias-clojure-version)]
      (-> (deps/clojure [alias])
          p/check))))
