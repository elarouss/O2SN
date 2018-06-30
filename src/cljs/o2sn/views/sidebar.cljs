(ns o2sn.views.sidebar
  (:require [reagent.core :as r]
            [re-frame.core :as rf]
            [secretary.core :as secretary]
            [o2sn.ui :as ui]))


(defn menu-item [{:keys [panel label icon]}]
  (let [active? @(rf/subscribe [:active-panel? panel])
        load-event (keyword (name panel) "load")]
    [ui/menu-item {:name (name panel)
                   :link true
                   :active active?
                   :disabled active?
                   :on-click #(do (secretary/dispatch! (str "/" (name panel)))
                                  (rf/dispatch [load-event]))}
     [ui/icon {:name icon}] label]))

(defn side-bar [panel]
  [ui/sidebar-pushable {:as (ui/component "Segment")}
   [ui/sidebar {:as (ui/component "Menu")
                :animation "push"
                :width "thin"
                :visible @(rf/subscribe [:sidebar-visible])
                :icon "labeled"
                :vertical true
                :color "teal"
                :inverted true}
    [menu-item {:panel  :home :label "Home" :icon  "home"}]
    [menu-item {:panel :channels :label "Channels" :icon "podcast"}]
    [menu-item {:panel :profile :label "Profile" :icon "user"}]
    [menu-item {:panel :messages :label "Messages" :icon "envelope"}]
    [menu-item {:panel :friends :label "Friends" :icon "users"}]
    [menu-item {:panel :settings :label "Settings" :icon "settings"}]]
   [ui/sidebar-pusher
    [ui/segment {:basic true}
     panel]]])
