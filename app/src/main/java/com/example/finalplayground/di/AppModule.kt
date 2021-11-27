package com.example.finalplayground.di

import com.example.finalplayground.data.network.SpaceXApi
import com.example.finalplayground.data.network.SpaceXApi.Companion.BASE_URL
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    /**
     * Initialises Retrofit API
     */
    @ExperimentalSerializationApi
    @Provides
    @Singleton
    fun provideSpaceXApi(): SpaceXApi {
        val contentType = MediaType.get("application/json")

        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(Json { ignoreUnknownKeys = true; isLenient = true }.asConverterFactory(contentType))
            .build()
            .create(SpaceXApi::class.java)
    }
}
