package com.example.finalplayground.domain.respository

import com.example.finalplayground.data.network.Resource
import com.example.finalplayground.domain.model.Launch
import com.example.finalplayground.domain.model.RocketDetails

/**
 * Repository layer for fetching data from either network or db layer.
 */
interface AppRepository {
    /**
     * Fetches the launches from the remote API.
     */
    suspend fun launches(): Resource<List<Launch>>

    /**
     * Fetches the rocket details for a launch
     */
    suspend fun oneRocket(id: String): Resource<RocketDetails>
}
