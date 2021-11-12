/*
* Team.kt
* viewer
*
* Created on 3/7/2null19
* Copyright 2null2null Citrus Circuits. All rights reserved.
*/

package com.example.viewer_2020.data

// Data storage class for each individual team object.
data class Team (var team_number: Int?) {
    var auto_high_balls_percent_inner: Float? = null
    var tele_high_balls_percent_inner: Float? = null
    var climb_all_success_avg_time: Float? = null
    var team_name: String? = null
    var climb_all_successes: Int? = null
    var climb_level_successes: Int? = null
    var park_successes: Int? = null
    var auto_line_successes: Int? = null
    var first_pickability: Float? = null
    var second_pickability: Float? = null
    var predicted_rps: Double? = null
    var predicted_rank: Int? = null
    var current_rps: Int? = null
    var current_rank: Int? = null
    var current_avg_rps: Float? = null
    var driver_quickness: Float? = null
    var driver_field_awareness: Float? = null
    var driver_ability: Float? = null
    var auto_avg_balls_low: Float? = null
    var auto_avg_balls_high: Float? = null
    var auto_avg_balls_total: Float? = null
    var tele_avg_balls_low: Float? = null
    var tele_avg_balls_high: Float? = null
    var tele_avg_balls_total: Float? = null
    var tele_cp_rotation_successes: Int? = null
    var tele_cp_position_successes: Int? = null
    var climb_all_attempts: Int? = null
    var climber_strap_installation_notes: String? = null
    var climber_strap_installation_difficulty: Int? = null
    var avg_incap_time: Float? = null
    var climb_percent_success: Float? = null
    var match_number: Int? = null
    var auto_balls_low: Int? = null
    var auto_balls_high: Int? = null
    var tele_balls_low: Int? = null
    var tele_balls_high: Int? = null
    var control_panel_rotation: Boolean? = null
    var control_panel_position: Boolean? = null
    var incap: Int? = null
    var climb_time: Int? = null
}
