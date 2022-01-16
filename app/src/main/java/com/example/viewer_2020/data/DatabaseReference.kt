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
        var pickability: MutableList<CalculatedPickAbilityTeam> = mutableListOf()
    )

    data class ObjectivePit (
        var team_number: Int,
        var drivetrain: Int, //value is an enum in schema
        var drivetrain_motors: Int,
        var drivetrain_motor_type: Int, //value is an enum in schema
        var has_ground_intake: Boolean,
        var can_eject_terminal: Boolean,
        var has_vision: Boolean,
        var can_cheescake: Boolean,
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
        var auto_line: Boolean,
        var climb: Boolean,
        var park: Boolean,
        var level_climb: Boolean
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
        var driver_quickness: Float,
        var driver_field_awareness: Float,
        var driver_ability: Float
    )

    data class CalculatedObjectiveTeam (
        var team_number: Int,
        var auto_avg_balls_low: Float,
        var auto_avg_balls_high: Float,
        var auto_avg_balls_total: Float,
        var tele_avg_balls_low: Float,
        var tele_avg_balls_high: Float,
        var tele_avg_balls_total: Float,
        var avg_incap_time: Float,
        var auto_sd_balls_low: Float,
        var auto_sd_balls_high: Float,
        var tele_sd_balls_low: Float,
        var tele_sd_balls_high: Float,
        var tele_cp_rotation_successes: Int,
        var tele_cp_position_successes: Int,
        var climb_all_attempts: Int,
        var matches_played: Int,
        var auto_max_balls_low: Int,
        var auto_max_balls_high: Int,
        var tele_max_balls_low: Int,
        var tele_max_balls_high: Int,
        var max_incap: Int
    )

    data class CalculatedObjectiveTeamInMatch (
        var confidence_rating: Int,
        var team_number: Int,
        var match_number: Int,
        var auto_balls_low: Int,
        var auto_balls_high: Int,
        var tele_balls_low: Int,
        var tele_balls_high: Int,
        var control_panel_rotation: Boolean,
        var control_panel_position: Boolean,
        var incap: Int,
        var climb_time: Int
    )
}
