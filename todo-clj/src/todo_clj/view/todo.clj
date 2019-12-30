(ns todo-clj.view.todo
    (:require 
      [hiccup.form :as hf]
      [todo-clj.view.layout :as layout]))

(defn error-messages [req]
  (when-let [errors (:errors req)]
            [:ul
             (for [[k v] errors
                   msg v]
                  [:li.error-message msg])]))

(defn todo-index-view [req todo-list]
  (->> [:section.card
        (when-let [{:keys [msg]} (:flash req)]
                  [:div.alert.alert-success [:strong msg]])
        [:h2 "TODO index"]
        [:ul
         (for [{:keys [id title]} todo-list]
              [:li [:a {:href (str "/todo/" id)} title]])]]
       (layout/common req)))

(defn todo-new-view [req]
  (->> [:section.card
        [:h2 "TODO Add"]
        (hf/form-to
          [:post "/todo/new"]
          (error-messages req)
          [:input {:name :title :placeholder "Input TODO"}]
          [:button.bg-blue "ADD"])]
       (layout/common req)))

(defn todo-show-view [req todo]
  (let [todo-id (:id todo)]
    (->> [:section.card
          (when-let [{:keys [msg]} (:flash req)]
                    [:div.alert.alert-success [:strong msg]])
          [:h2 (:title todo)]
          [:div [:a.wide-link {:href (str "/todo/" todo-id "/edit")} "EDIT"]]
          [:div [:a.wide-link {:href (str "/todo/" todo-id "/delete")} "DELETE"]]]
         (layout/common req))))

(defn todo-edit-view [req todo]
  (let [todo-id (get-in req [:params :todo-id])]
    (->> [:section.card
          [:h2 "Edit TODO"]
          (hf/form-to
            [:post (str "/todo/" todo-id "/edit")]
            (error-messages req)
            [:input {:name :title :value (:title todo)
                     :placeholder "Input TODO"}]
            [:button.bg-blue "Update"])]
         (layout/common req))))

(defn todo-delete-view [req todo]
  (let [todo-id (get-in req [:params :todo-id])]
    (->> [:section.card
          [:h2 "Delete TODO"]
          (hf/form-to
            [:post (str "/todo/" todo-id "/delete")]
            [:p "Are you sure?"]
            [:p "*" (:title todo)]
            [:button.bg-red "DELETE"])]
         (layout/common req))))
