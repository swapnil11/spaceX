package com.example.finalplayground.domain.usecases

import com.example.finalplayground.domain.respository.AppRepository
import javax.inject.Inject

/**
 * Usecase layer responsible for fetching data from the repository layer.
 *
 * @param repository DI injected repository
 */
class SpaceXUseCase @Inject constructor(private val repository: AppRepository) {
    /**
     * Fetches the launches from the remote API.
     */
    suspend fun launches() = repository.launches()

    /**
     * Fetches the rocket details for a launch
     */
    suspend fun oneRocket(id: String) = repository.oneRocket(id)
}
