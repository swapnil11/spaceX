package com.example.finalplayground.data

import com.example.finalplayground.data.network.Resource
import com.example.finalplayground.data.network.ResponseHandler
import com.example.finalplayground.data.network.SpaceXApi
import com.example.finalplayground.domain.model.Launch
import com.example.finalplayground.domain.model.RocketDetails
import com.example.finalplayground.domain.respository.AppRepository
import javax.inject.Inject

/**
 * Repository layer for fetching data from either network or db layer.
 */
class AppRepositoryImpl @Inject constructor(
    private val api: SpaceXApi
) : AppRepository {

    /**
     * Fetches the launches from the remote API.
     */
    override suspend fun launches(): Resource<List<Launch>> = try {
        ResponseHandler.handleSuccess(api.launches())
    } catch (e: Exception) {
        ResponseHandler.handleException(e)
    }

    /**
     * Fetches the rocket details for a launch
     */
    override suspend fun oneRocket(id: String): Resource<RocketDetails> = try {
        ResponseHandler.handleSuccess(api.oneRocket(id))
    } catch (e: Exception) {
        ResponseHandler.handleException(e)
    }
}
