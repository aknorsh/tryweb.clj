(ns todo-clj.handler.main
    (:require [compojure.core :refer [defroutes GET]]
     [compojure.route :as route]
     [todo-clj.util.response :as res]))

(defn home-view [req]
  "<h1>HOME</h1>
   <a href=\"/todo\">TODO</a>")

(defn home [req]
  (-> (home-view req)
      res/response
      res/html))

(defroutes main-routes
  (GET "/" _ home)
  (route/not-found "<h1>404 page not found.</h1>"))
