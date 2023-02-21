/*
* getTeamObjectByKey.kt
* viewer
*
* Created on 2/18/2020
* Copyright 2020 Citrus Circuits. All rights reserved.
*/

package org.citruscircuits.viewer

import kotlinx.serialization.json.jsonPrimitive

// Returns a string value of any team object in the database as long as you provide it with
// the team number and the requested field.
// 'path' is for whichever branch of alliances you want (ex. calc_obj_team).

// It WILL iterate through every object in the given path until it finds the correct one which is a
// bit heavy, yet there's no obvious better way to do it given the structure of our database.

// If the value cannot be found, then it returns whatever character is set in Constants -> NULL_CHARACTER.
fun getTeamObjectByKey(teamNumber: String, field: String): String? {
    return if (StartupActivity.databaseReference?.team?.contains(teamNumber) == true) {

        val teamDocument = StartupActivity.databaseReference!!.team[teamNumber]!!

        if (teamDocument.contains(field)) {
            teamDocument[field]?.jsonPrimitive?.content
        } else null
    } else null
}