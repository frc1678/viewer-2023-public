/*
* TeamInMatch.kt
* viewer
*
* Created on 3/7/2019
* Copyright 2020 Citrus Circuits. All rights reserved.
*/

package com.example.viewer_2020.data

// Data storage class for each individual team in match object.
data class TeamInMatch(
    var team_number: Int? = null,
    var match_number: Int? = null
) {
    var start_position: String? = null
    var quintet: Boolean? = null
    var auto_balls_low: Int? = null
    var auto_balls_high: Int? = null
    var auto_line: Boolean? = null
    var tele_balls_low: Int? = null
    var tele_balls_high: Int? = null
    var tele_lows: Int? = null
    var tele_launchpad_highs: Int? = null
    var tele_hub_highs: Int? = null
    var tele_other_highs: Int? = null
    var climb_time: Int? = null
    var climb_level: String? = null
    var exit_ball_catches: Int? = null
    var opp_balls_scored: Int? = null
    var intakes: Int? = null
    var incap: Int? = null
}