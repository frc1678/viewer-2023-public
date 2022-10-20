/*
* getAllianceMatchObjectByKey.kt
* viewer
*
* Created on 2/18/2020
* Copyright 2020 Citrus Circuits. All rights reserved.
*/

package com.example.viewer_2022

import com.example.viewer_2022.constants.Constants
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.json.jsonPrimitive

// Returns a string value of any alliance object in the database as long as you provide it with
// the match number and the alliance of the color (and the requested field).
// 'path' is for whichever branch of alliances you want (ex. predicted_aim).

// It WILL iterate through every object in the given path until it finds the correct one which is a
// bit heavy, yet there's no obvious better way to do it given the structure of our database.

// If the value cannot be found, then it returns whatever character is set in Constants -> NULL_CHARACTER.
fun getAllianceInMatchObjectByKey(
    path: String,
    allianceColor: String,
    matchNumber: String,
    field: String
): String {
    for ((_, collectionElement) in StartupActivity.databaseReference!![path]!!.jsonObject) {
        val collectionObject = collectionElement.jsonObject
        if (collectionObject["match_number"]!!.jsonPrimitive.content == matchNumber) {
            if (collectionObject["alliance_color_is_red"]!!.jsonPrimitive.content == "true" &&
                allianceColor == "red"
            )
                return collectionObject[field]!!.jsonPrimitive.content
            else if (collectionObject["alliance_color_is_red"]!!.jsonPrimitive.content == "false" &&
                allianceColor == "blue"
            )
                return collectionObject[field]!!.jsonPrimitive.content
        }
    }
    return Constants.NULL_CHARACTER
}