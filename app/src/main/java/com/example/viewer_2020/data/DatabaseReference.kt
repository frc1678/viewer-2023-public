/*
* DatabaseReference.kt
* viewer
*
* Created on 2/2/2020
* Copyright 2020 Citrus Circuits. All rights reserved.
*/

package com.example.viewer_2020.data

import org.bson.types.ObjectId

//Database reference class to make a database object from MongoDB.
class DatabaseReference {
    data class CompetitionObject (
        var raw_obj_pit: MutableList<ObjectivePit> = mutableListOf(),
        var obj_tim: MutableList<CalculatedObjectiveTeamInMatch> = mutableListOf(),
        var obj_team: MutableList<CalculatedObjectiveTeam> = mutableListOf(),
        var subj_team: MutableList<CalculatedSubjectiveTeam> = mutableListOf(),
        var predicted_aim: MutableList<CalculatedPredictedAllianceInMatch> = mutableListOf(),
        var predicted_team: MutableList<CalculatedPredictedTeam> = mutableListOf(),
        var tba_team: MutableList<CalculatedTBATeam> = mutableListOf(),
<<<<<<< HEAD
        var pickability: MutableList<CalculatedPickAbilityTeam> = mutableListOf(),
        var tba_tim: MutableList<CalculatedTBATeamInMatch> = mutableListOf()
=======
        var tba_tim: MutableList<CalculatedTBATeamInMatch> = mutableListOf(),
        var pickability: MutableList<CalculatedPickAbilityTeam> = mutableListOf()
>>>>>>> c1a46ba52b23ea6b658f53ba4b8e39295252ee31
    )

    data class ObjectivePit (
        var team_number: Int,
        var drivetrain: Int, //value is an enum in schema
        var drivetrain_motors: Int,
        var drivetrain_motor_type: Int, //value is an enum in schema
        var has_ground_intake: Boolean,
        var can_eject_terminal: Boolean,
        var has_vision: Boolean,
        var can_cheesecake: Boolean,
        var can_intake_terminal: Boolean,
        var can_under_low_rung: Boolean,
        var can_climb: Boolean
    )


    data class CalculatedPredictedAllianceInMatch (
        var match_number: Int,
        var alliance_color_is_red: Boolean,
        var has_actual_data: Boolean,
        var actual_score: Int,
        var actual_rp1: Float,
        var actual_rp2: Float,
        var predicted_score: Float,
        var predicted_rp1: Float,
        var predicted_rp2: Float
    )

    data class CalculatedTBATeam (
        var team_number: Int,
        var team_name: String,
        var auto_line_successes: Int
    )

    data class CalculatedTBATeamInMatch (
        var team_number: Int,
        var match_number: Int,
        var auto_line: Boolean
    )

    data class CalculatedPickAbilityTeam (
        var team_number: Int,
        var first_pickability: Float,
        var second_pickability: Float
    )

    data class CalculatedPredictedTeam (
        var team_number: Int,
        var predicted_rps: Double,
        var predicted_rank: Int,
        var current_rps: Int,
        var current_rank: Int,
        var current_avg_rps: Float
    )

    data class CalculatedSubjectiveTeam (
        var team_number: Int,
        var driver_near_field_awareness: Float,
        var driver_far_field_awareness: Float,
        var driver_quickness: Float,
        var driver_ability: Float
    )

    data class CalculatedObjectiveTeam (
        var team_number: Int,
        var auto_avg_near_hub_highs: Float,
        var auto_avg_far_hub_highs: Float,
        var auto_avg_launchpad_highs: Float,
        var auto_avg_near_other_highs: Float,
        var auto_avg_far_other_highs: Float,
        var auto_avg_near_hub_lows: Float,
        var auto_avg_far_hub_lows: Float,
        var tele_avg_near_hub_highs: Float,
        var tele_avg_far_hub_highs: Float,
        var tele_avg_launchpad_highs: Float,
        var tele_avg_near_other_highs: Float,
        var tele_avg_far_other_highs: Float,
        var tele_avg_near_hub_lows: Float,
        var tele_avg_far_hub_lows: Float,
        var auto_avg_balls_low: Float,
        var auto_avg_balls_high: Float,
        var auto_avg_balls_total: Float,
        var tele_avg_balls_low: Float,
        var tele_avg_balls_high: Float,
        var tele_avg_balls_total: Float,
        var avg_incap_time: Float,
        var avg_intakes: Float,
        var avg_exit_ball_catches: Float,
        var avg_opp_balls_scored: Float,
        var lfm_auto_avg_near_hub_highs: Float,
        var lfm_auto_avg_far_hub_highs: Float,
        var lfm_auto_avg_launchpad_highs: Float,
        var lfm_auto_avg_near_other_highs: Float,
        var lfm_auto_avg_far_other_highs: Float,
        var lfm_auto_avg_near_hub_lows: Float,
        var lfm_auto_avg_far_hub_lows: Float,
        var lfm_tele_avg_near_hub_highs: Float,
        var lfm_tele_avg_far_hub_highs: Float,
        var lfm_tele_avg_launchpad_highs: Float,
        var lfm_tele_avg_near_other_highs: Float,
        var lfm_tele_avg_far_other_highs: Float,
        var lfm_tele_avg_near_hub_lows: Float,
        var lfm_tele_avg_far_hub_lows: Float,
        var lfm_auto_avg_balls_low: Float,
        var lfm_auto_avg_balls_high: Float,
        var lfm_tele_avg_balls_low: Float,
        var lfm_tele_avg_balls_high: Float,
        var lfm_avg_incap_time: Float,
        var lfm_avg_exit_ball_catches: Float,
        var lfm_avg_opp_balls_scored: Float,
        var auto_sd_balls_low: Float,
        var auto_sd_balls_high: Float,
        var tele_sd_balls_low: Float,
        var tele_sd_balls_high: Float,
        var climb_all_attempts: Int,
        var low_rung_successes: Int,
        var mid_rung_successes: Int,
        var high_rung_successes: Int,
        var traversal_rung_successes: Int,
        var matches_played: Int,
        var matches_incap: Int,
        var lfm_climb_all_attempts: Int,
        var lfm_low_rung_successes: Int,
        var lfm_mid_rung_successes: Int,
        var lfm_high_rung_successes: Int,
        var lfm_traversal_rung_successes: Int,
        var lfm_matches_incap: Int,
        var auto_max_balls_low: Int,
        var auto_max_balls_high: Int,
        var tele_max_balls_low: Int,
        var tele_max_balls_high: Int,
        var max_incap: Int,
        var max_exit_ball_catches: Int,
        var max_opp_balls_scored: Int,
        var max_climb_level: String,
        var lfm_auto_max_balls_low: Int,
        var lfm_auto_max_balls_high: Int,
        var lfm_tele_max_balls_low: Int,
        var lfm_tele_max_balls_high: Int,
        var lfm_max_incap: Int,
        var lfm_max_exit_ball_catches: Int,
        var lfm_max_opp_balls_scored: Int,
        var lfm_max_climb_level: String,
        var mode_climb_level: String,
        var mode_start_position: String,
        var lfm_mode_start_position: String,
        var low_avg_time: Float,
        var mid_avg_time: Float,
        var high_avg_time: Float,
        var traversal_avg_time: Float,
        var lfm_low_avg_time: Float,
        var lfm_mid_avg_time: Float,
        var lfm_high_avg_time: Float,
        var lfm_traversal_success_avg_time: Float,
        var climb_percent_success: Float,
        var lfm_climb_percent_success: Float
    )

    data class CalculatedObjectiveTeamInMatch (
        var confidence_rating: Int,
        var team_number: Int,
        var match_number: Int,
        var auto_near_hub_high: Int,
        var auto_far_hub_highs: Int,
        var auto_launchpad_highs: Int,
        var auto_near_other_highs: Int,
        var auto_far_other_highs: Int,
        var auto_near_hub_lows: Int,
        var auto_far_hub_lows: Int,
        var tele_near_hub_highs: Int,
        var tele_far_hub_highs: Int,
        var tele_launchpad_highs: Int,
        var tele_near_other_highs: Int,
        var tele_far_other_highs: Int,
        var tele_near_hub_lows: Int,
        var tele_far_hub_lows: Int,
        var intakes: Int,
        var exit_ball_catches: Int,
        var opp_balls_scored: Int,
        var auto_balls_low: Int,
        var auto_balls_high: Int,
        var tele_balls_low: Int,
        var tele_balls_high: Int,
        var climb_level: String,
        var start_position: String,
        var incap: Int,
        var climb_time: Int
    )
}
