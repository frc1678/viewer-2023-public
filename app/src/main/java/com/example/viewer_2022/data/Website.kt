package com.example.viewer_2022.data

import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type

class Website {

    data class WebsiteMatchTeam(val color: String, val number: Int)

    data class WebsiteMatch(val teams: ArrayList<WebsiteMatchTeam>)
}

val WebsiteMatchSchedule: Type =
    object : TypeToken<MutableMap<String, Website.WebsiteMatch>>() {}.type
val WebsiteTeams: Type = object : TypeToken<List<String>>() {}.type
