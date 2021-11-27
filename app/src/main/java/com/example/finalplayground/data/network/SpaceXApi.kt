package com.example.finalplayground.data.network

import com.example.finalplayground.domain.model.Launch
import com.example.finalplayground.domain.model.RocketDetails
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * API class for defining endpoint and request for the application
 */
interface SpaceXApi {
    /**
     * Suspend function which fetches the [List] of [Launch] from the [LAUNCH_ENDPOINT].
     * @return list of [Launch]
     */
    @GET(LAUNCH_ENDPOINT)
    suspend fun launches(): List<Launch>

    /**
     * Suspend function which fetches the [RocketDetails] from the [ONE_ROCKET_ENDPOINT].
     * @return [RocketDetails]
     */
    @GET(ONE_ROCKET_ENDPOINT)
    suspend fun oneRocket(@Path("id") id: String): RocketDetails

    companion object {
        const val BASE_URL = "https://api.spacexdata.com/"
        private const val LAUNCH_ENDPOINT = "v4/launches"
        private const val ONE_ROCKET_ENDPOINT = "v4/rockets/{id}"
    }
}
