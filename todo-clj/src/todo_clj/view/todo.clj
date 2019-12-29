(ns todo-clj.view.todo
    (:require [hiccup.core :as hc]))

(defn todo-index-view [req todo-list]
  (-> `([:h1 "Todos"]
         [:ul
          ~@(for [{:keys [title]} todo-list]
                 [:li title])])
    hc/html))


