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
        var raw_subj_pit: MutableList<SubjectivePit> = mutableListOf(),
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
        var can_cross_trench: Boolean,
        var drivetrain: Int, //IF APP CRASHES,
        //THIS VALUE IS ACTUALLY AN ENUM BUT I HAVE NO IDEA WHY THEY THOUGHT IT
        //WAS A GOOD IDEA TO HAVE IT AS AN ENUM WHEN WE OBVIOUSLY CANNOT HANDLE ENUMS
        var drivetrain_motors: Int,
        var drivetrain_motor_type: Int, //IF APP CRASHES,
        //THIS VALUE IS ACTUALLY AN ENUM BUT I HAVE NO IDEA WHY THEY THOUGHT IT
        //WAS A GOOD IDEA TO HAVE IT AS AN ENUM WHEN WE OBVIOUSLY CANNOT HANDLE ENUMS
        var has_ground_intake: Boolean
    )

    data class SubjectivePit (
        var team_number: Int,
        var climber_strap_installation_notes: String,
        var climber_strap_installation_difficulty: Int //IF APP CRASHES,
        //THIS VALUE IS ACTUALLY AN ENUM BUT I HAVE NO IDEA WHY THEY THOUGHT IT
        //WAS A GOOD IDEA TO HAVE IT AS AN ENUM WHEN WE OBVIOUSLY CANNOT HANDLE ENUMS
    )

    data class CalculatedPredictedAllianceInMatch (
        var match_number: Int,
        var alliance_color_is_red: Boolean,
        var predicted_score: Float,
        var predicted_rp1: Float,
        var predicted_rp2: Float
    )

    data class CalculatedTBATeam (
        var team_number: Int,
        var team_name: String,
        var climb_all_success_avg_time: Float,
        var climb_all_successes: Int,
        var climb_level_successes: Int,
        var park_successes: Int,
        var auto_line_successes: Int
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
        var driver_agility: Float,
        var driver_speed: Float,
        var driver_ability: Float
    )

    data class CalculatedObjectiveTeam (
        var team_number: Int,
        var auto_avg_balls_low: Float,
        var auto_avg_balls_high: Float,
        var tele_avg_balls_low: Float,
        var tele_avg_balls_high: Float,
        var tele_cp_rotation_successes: Int,
        var tele_cp_position_successes: Int,
        var climb_all_attempts: Int
    )

    data class CalculatedObjectiveTeamInMatch (
        var team_number: Int,
        var match_number: Int,
        var auto_balls_low: Int,
        var auto_balls_high: Int,
        var tele_balls_low: Int,
        var tele_balls_high: Int,
        var control_panel_rotation: Boolean,
        var control_panel_position: Boolean,
        var timeline_cycle_time: Int
    )
}
