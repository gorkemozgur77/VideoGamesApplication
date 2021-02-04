package com.example.videogamesapplication.firebase

import android.os.Bundle
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase
import kotlinx.android.synthetic.main.fragment_gamedetails.*

class FirebaseService {
    enum class EventType(val1 : String, val2 : String) {

        HOME_PAGE("HomePage", "GamelistFragment"),
        DETAIL_PAGE("DetailPage","GameDetailsFragment"),
        FAVORITE_PAGE("FavoritePage","FavoritesFragment");

        val screenName = val1
        val screenClass = val2
    }

    enum class ButtonType(val1 : String){

        ADDING_FAVOURITE("Adding Favorite"),
        REMOVE_FAVOURITE("Remove Favorite");

        val buttonEvent  = val1

    }

    fun logEvent(type : EventType) {
        Firebase.analytics.logEvent(FirebaseAnalytics.Event.SCREEN_VIEW) {
            param(FirebaseAnalytics.Param.SCREEN_NAME, type.screenName)
            param(FirebaseAnalytics.Param.SCREEN_CLASS, type.screenClass)
        }
    }

    fun logButton(buttonType: ButtonType, buttonId: Int) {
        val bundle = Bundle()
        bundle.putInt("ButtonID", buttonId)
        bundle.putString("ButtonEvent", buttonType.buttonEvent)
        Firebase.analytics.logEvent("FavoriteButtonEvents", bundle)
    }
}