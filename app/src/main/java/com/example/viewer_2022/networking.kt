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
import kotlinx.serialization.DeserializationStrategy
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonContentPolymorphicSerializer
import kotlinx.serialization.json.JsonElement
import kotlinx.serialization.json.jsonObject
import kotlinx.serialization.serializer

const val grosbeakURL = "https://grosbeak.citruscircuits.org"

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
    suspend fun setPicklist(picklist: PicklistData, password: String, eventKey: String? = null): PicklistSetResponse = client.put("$grosbeakURL/picklist/rest/list") {
        parameter("password", password)
        if (eventKey != null) {
            parameter("event_key", eventKey)
        }
        contentType(ContentType.Application.Json)
        setBody(picklist)
    }.body()

    @Serializable(with = PicklistSetSerializer::class)
    sealed class PicklistSetResponse {
        @Serializable
        data class Success(val deleted: Int) : PicklistSetResponse()

        @Serializable
        data class Error(val error: String) : PicklistSetResponse()

    }
    object PicklistSetSerializer : JsonContentPolymorphicSerializer<PicklistSetResponse>(PicklistSetResponse::class) {
        override fun selectDeserializer(element: JsonElement): DeserializationStrategy<out PicklistSetResponse> = when {
            element.jsonObject.containsKey("error") -> PicklistSetResponse.Error.serializer()
            element.jsonObject.containsKey("deleted") -> PicklistSetResponse.Success.serializer()
            else -> throw IllegalArgumentException("Unknown response type")
        }
    }

}

