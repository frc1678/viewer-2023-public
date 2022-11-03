package com.example.viewer_2022.data

import com.google.gson.reflect.TypeToken
import kotlinx.serialization.Serializable
import java.lang.reflect.Type


@Serializable
data class MatchScheduleMatchTeam(val color: String, val number: Int)

@Serializable
data class MatchScheduleMatch(val teams: ArrayList<MatchScheduleMatchTeam>)