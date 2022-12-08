package com.example.viewer_2022.data

import kotlinx.serialization.Serializable


@Serializable
data class MatchScheduleMatchTeam(val color: String, val number: String)

@Serializable
data class MatchScheduleMatch(val teams: ArrayList<MatchScheduleMatchTeam>)