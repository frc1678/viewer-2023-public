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

// If the value cannot be found, then it returns null.
fun getRawObjectByKey(teamNumber: String, field: String): String? {

    return StartupActivity.databaseReference?.team?.get(teamNumber)?.get(field)?.jsonPrimitive?.content
}