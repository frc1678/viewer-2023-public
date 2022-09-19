package com.example.viewer_2022

import com.example.viewer_2022.fragments.offline_picklist.PicklistData
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.cio.*
import io.ktor.client.plugins.*
import io.ktor.client.request.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable

const val grosbeakURL = "http://localhost:8000"

val client = HttpClient(CIO) {
    install(ContentNegotiation) {
        json()
    }
    defaultRequest {
        header("Authorization", "02ae3a526cf54db9b563928b0ec05a77")
        host = "localhost"
        port = 8000
    }
}

object PicklistApi {
    suspend fun getPicklist(eventKey: String? = null): PicklistData = client.get("$grosbeakURL/picklist/rest/list") {
        if (eventKey != null) {
            parameter("event_key", eventKey)
        }
    }.body()
    suspend fun setPicklist(picklist: PicklistData, eventKey: String? = null): PicklistSetResponse = client.put("$grosbeakURL/picklist/rest/list") {
        if (eventKey != null) {
            parameter("event_key", eventKey)
        }
        contentType(ContentType.Application.Json)
        setBody(picklist)
    }.body()

    @Serializable
    data class PicklistSetResponse(val deleted: Int)
}