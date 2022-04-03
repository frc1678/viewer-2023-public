/*
* TeamInMatch.kt
* viewer
*
* Created on 3/7/2019
* Copyright 2020 Citrus Circuits. All rights reserved.
*/

package com.example.viewer_2022.data

/**
 * Data storage class for each individual TIM object.
 */
data class TeamInMatch(
    var team_number: Int? = null,
    var match_number: Int? = null
) {
    var objTim: DatabaseReference.CalculatedObjectiveTeamInMatch? = null
    var subjTim: DatabaseReference.CalculatedSubjectiveTeamInMatch? = null
    var tbaTim: DatabaseReference.CalculatedTBATeamInMatch? = null
}
