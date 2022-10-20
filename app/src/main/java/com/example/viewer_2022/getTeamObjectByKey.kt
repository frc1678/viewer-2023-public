/*
* getTeamObjectByKey.kt
* viewer
*
* Created on 2/18/2020
* Copyright 2020 Citrus Circuits. All rights reserved.
*/

package com.example.viewer_2022

import com.example.viewer_2022.constants.Constants
import com.example.viewer_2022.data.Team
import kotlinx.serialization.json.jsonArray
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

// Returns a string value of any team object in the database as long as you provide it with
// the team number and the requested field.
// 'path' is for whichever branch of alliances you want (ex. calc_obj_team).

// It WILL iterate through every object in the given path until it finds the correct one which is a
// bit heavy, yet there's no obvious better way to do it given the structure of our database.

// If the value cannot be found, then it returns whatever character is set in Constants -> NULL_CHARACTER.
fun getTeamObjectByKey(teamNumber: String, field: String): String {
    if (StartupActivity.databaseReference.team.has
        MainViewerActivity.teamCache.containsKey(teamNumber)) {
        // Two separate if statements to lower computations per interaction.
        if (
            getDirectField(
                MainViewerActivity.teamCache[teamNumber]!!,
                field
            ).toString() != Constants.NULL_CHARACTER
        ) {
            return getDirectField(MainViewerActivity.teamCache[teamNumber]!!, field).toString()
        }
    } else {
        // If the team object doesn't exist in the cache, create the team object in the team cache.
        MainViewerActivity.teamCache[teamNumber] = Team(teamNumber.toInt())
    }
    // This for loop will occur when the team does NOT exist in the cache, AND when the team DOES
    // exist in the cache but has a null value for the given field.
    for (collectionObject in StartupActivity.databaseReference!![path]!!.jsonArray) {

        // create a map of the collection data where the key is the team number and the data is another dictionary with key and value
        // check if team number in the json object
        // then check the field exists and then set teamCach[field] = the value of the field
        if (collectionObject.jsonObject["team_number"]!!.jsonPrimitive.content == teamNumber) {
            //TODO: Fix Team Cache stuff (Kate)

            // Returns the value of the specific field
            return collectionObject.jsonObject[field]!!.jsonPrimitive.content

        }
    }
    return Constants.NULL_CHARACTER
}