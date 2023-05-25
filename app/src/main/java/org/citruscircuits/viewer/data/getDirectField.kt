package org.citruscircuits.viewer

import org.citruscircuits.viewer.constants.Constants

// Returns the value of the datapoint provided by findFieldInInheritedFields.
@Throws(Exception::class)
fun getDirectField(`object`: Any, field: String): Any {
    return try {
        if (`object` is List<*>) {
            `object`[field.toInt()]!!
        } else {
            findFieldInInheritedFields(`object`::class.java, field)[`object`]!!
        }
    } catch (e: Exception) {
        return Constants.NULL_CHARACTER
    }
}