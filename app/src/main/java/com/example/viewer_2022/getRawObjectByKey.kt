/*
* getRawObjectByKey.kt
* viewer
*
* Created on 2/26/2020
* Copyright 2020 Citrus Circuits. All rights reserved.
*/

package com.example.viewer_2022

import com.example.viewer_2022.constants.Constants
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

// Returns a string value of any raw object in the database as long as you provide it with
// the team number and the requested field.
// 'path' is for whichever branch of alliances you want (ex. obj_pit).

// It WILL iterate through every object in the given path until it finds the correct one which is a
// bit heavy, yet there's no obvious better way to do it given the structure of our database.

// If the value cannot be found, then it returns whatever character is set in Constants -> NULL_CHARACTER.
fun getRawObjectByKey(path: String, teamNumber: String, field: String): String {
    for ((_, collectionElement) in StartupActivity.databaseReference!![path]!!.jsonObject) {
        val collectionObject = collectionElement.jsonObject
        if (collectionObject["team_number"]!!.jsonPrimitive.content == teamNumber) {
            return collectionObject[field]!!.jsonPrimitive.content
        }
    }

    return Constants.NULL_CHARACTER
}