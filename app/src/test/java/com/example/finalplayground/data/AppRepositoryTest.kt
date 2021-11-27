package com.example.finalplayground.data

import com.example.finalplayground.BaseTest
import com.example.finalplayground.data.network.Resource
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class AppRepositoryTest : BaseTest() {

    @Test
    fun testSuccessResponse() {
        setResponse("launches.json")
        runBlocking {
            Assert.assertTrue(
                repository.launches().status == Resource.Status.SUCCESS
            )
        }
    }

    @Test
    fun testFailResponse() {
        setErrorResponse()
        runBlocking {
            Assert.assertTrue(
                repository.launches().status == Resource.Status.ERROR
            )
        }
    }

    @Test
    fun testLaunchItems() {
        setResponse("launches.json")
        runBlocking {
            val expectedItems = 146 // in local json file, we have 146 items.
            Assert.assertEquals(
                repository.launches().data?.size, expectedItems
            )
        }
    }

    @Test
    fun testInvalidDataResponse() {
        setEmptyResponse()
        runBlocking {
            val expectedItems = 0
            val response = repository.launches()
            Assert.assertTrue(
                response.status == Resource.Status.SUCCESS
            )
        }
    }
}
