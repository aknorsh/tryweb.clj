(defn handler [req]
  {:status 200
   :headers {"Content-Type" "text/plain"}
   :body (:remote-addr req)})

(defn wrap-exclamation-mark [handler]
  (fn [request]
      (let [response (handler request)]
        (update response :body #(str % "!!")))))

(defn wrap-parse-query-string [handler]
  (fn [request]
      (let [params (parse-query-string (:query-string request))
            updated-request (assoc request :params params)]
        (handler updated-request))))

(def app
  (-> handler
      wrap-exclamation-mark
      wrap-parse-query-string))
