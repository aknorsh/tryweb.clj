(ns todo-clj.view.main
    (:require [todo-clj.view.layout :as layout]))

(defn home-view [req]
  (->> [:section.card
        [:h2 "Home"]
        [:a {:href "/todo"} "TODO"]]
       (layout/common req)))
