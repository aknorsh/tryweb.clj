(ns todo-clj.core
    (:require [compojure.core :refer [defroutes context GET]]
              [compojure.route :as route]
              [ring.adapter.jetty :as server]
              [ring.util.response :as res]))

; SERVER

(defonce server (atom nil))

(defn handler [req]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body "Hello, World!"})

(defn start-server []
  (when-not @server
    (reset! server (server/run-jetty #'handler {:port 3000 :join? false}))))

(defn stop-server []
  (when @server
    (.stop @server)
    (reset! server nil)))

(defn restart-server []
  (when @server
    (stop-server)
    (start-server)))


; ROUTING

(defn html [res]
  (res/content-type res "text/html; charset=utf-8"))


(defn home-view [req]
  "<h1>HOME</h1>
   <a href=\"/todo\">TODO</a>")

(defn home [req]
  (-> (home-view req)
      res/response
      html))

(def todo-list
  [{:title "Take a train"}
   {:title "Ride an elevator"}
   {:title "Work"}
   {:title "Go home"}])

(defn todo-index-view [req]
  `("<h1>TODO</h1>"
    "<ul>"
    ~@(for [{:keys [title]} todo-list]
           (str "<li>" title "</li>"))
    "</ul>"))

(defn todo-index [req]
  (-> (todo-index-view req)
      res/response
      html))

(def routes
  {"/" home
   "/todo" todo-index})

(defn match-route [uri]
  (get routes uri))

(defn handler [req]
  (let [uri (:uri req)
        maybe-fn (match-route uri)]
    (if maybe-fn
        (maybe-fn req)
        (not-found))))

; with compojure

(defroutes handler
  (GET "/" req home)
  (GET "/todo" req todo-index)
  (route/not-found "<h1>404 page not found.</h1>"))
