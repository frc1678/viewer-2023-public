package org.citruscircuits.viewer

import java.lang.reflect.Field

// Returns the value of the given field of type object.
@Throws(Exception::class)
fun findFieldInInheritedFields(
    `object`: Class<*>,
    field: String
): Field {
    return try {
        // If the given class object contains the field as one of its fields, then return the field's value.
        val value = `object`.getDeclaredField(field)
        value.isAccessible = true
        value
    } catch (e: Exception) {
        // If this is reached, it means that the field you inputted doesn't exist in DatabaseReference.
        findFieldInInheritedFields(`object`.superclass!!, field)
    }
}