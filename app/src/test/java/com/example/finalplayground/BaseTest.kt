package com.example.finalplayground

import com.example.finalplayground.data.AppRepositoryImpl
import com.example.finalplayground.data.network.SpaceXApi
import com.example.finalplayground.domain.model.Launch
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import okhttp3.MediaType
// import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import retrofit2.Retrofit

abstract class BaseTest {

    var mockWebServer = MockWebServer()

    @ExperimentalSerializationApi
    private val api = Retrofit.Builder()
        .baseUrl(mockWebServer.url("").toString())
        .addConverterFactory(Json.asConverterFactory(MediaType.get("application/json")))
        .build()
        .create(SpaceXApi::class.java)

    @ExperimentalSerializationApi
    var repository = AppRepositoryImpl(api)

    fun setResponse(fileName: String) {
        val input = this::class.java.classLoader?.getResourceAsStream(fileName) ?: return
        val decodedData = Json { ignoreUnknownKeys = true }.decodeFromStream<List<Launch>>(input)
        mockWebServer.enqueue(
            MockResponse().setResponseCode(200)
                .setBody(Json { ignoreUnknownKeys = true }.encodeToString(decodedData))
        )
    }

    fun setEmptyResponse() {
        mockWebServer.enqueue(MockResponse().setResponseCode(200).setBody("[]"))
    }

    fun setErrorResponse() {
        mockWebServer.enqueue(MockResponse().setResponseCode(400).setBody("{}"))
    }
}
