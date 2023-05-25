package org.citruscircuits.viewer

import kotlinx.serialization.json.jsonPrimitive

// Returns a string value of any raw object in the database as long as you provide it with
// the team number and the requested field.

// If the value cannot be found, then it returns null.
fun getRawObjectByKey(teamNumber: String, field: String): String? {

    return StartupActivity.databaseReference?.team?.get(teamNumber)
        ?.get(field)?.jsonPrimitive?.content
}