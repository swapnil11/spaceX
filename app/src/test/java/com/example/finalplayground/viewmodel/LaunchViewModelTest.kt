package com.example.finalplayground.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.finalplayground.data.network.Resource
import com.example.finalplayground.domain.model.Launch
import com.example.finalplayground.domain.model.RocketDetails
import com.example.finalplayground.domain.usecases.SpaceXUseCase
import com.example.finalplayground.ui.viewmodel.LaunchViewModel
import com.example.finalplayground.ui.viewmodel.Mode
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import junit.framework.Assert.assertEquals
import junit.framework.Assert.assertTrue
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit

class LaunchViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    @MockK
    private lateinit var spaceXUseCase: SpaceXUseCase

    private lateinit var launchViewModel: LaunchViewModel

    var launches = readJson<List<Launch>>("launches.json") // Reading mock json
    var oneRocket = readJson<RocketDetails>("oneRocket.json")

    @Before
    fun setup() {
        MockKAnnotations.init(this, relaxUnitFun = true)
        coEvery { spaceXUseCase.launches() } returns Resource.success(launches!!)
        coEvery { spaceXUseCase.oneRocket("") } returns Resource.success(oneRocket!!)

        launchViewModel = LaunchViewModel(spaceXUseCase)
    }

    @Test
    fun testLaunchDetailViewModel() {
        runBlocking {
            launchViewModel.remoteLaunches.observeForever {
                if (it.status == Resource.Status.SUCCESS) {
                    Assert.assertEquals(launches, it.data)
                }
            }
        }
    }

    @Test
    fun testDataTransformation() {
        val items = launchViewModel.mapOfLaunches(launches!!)
        Assert.assertEquals(items.size, 0)
    }

    @Test
    fun testDataTransformationLaunchDateWithSorting() {
        val latch = CountDownLatch(3)
        val observer = Observer<Resource<List<Launch>>> {
            latch.countDown()
            if (latch.count == 0L) {
                assertEquals(it.data?.first()?.launchYear, "2006")
                assertEquals(it.data?.last()?.launchYear, "2022")
            }
        }
        launchViewModel.remoteLaunches.observeForever(observer)
        launchViewModel.refresh(Mode.SORT_LAUNCH_DATE, launches!!)
        latch.await(100, TimeUnit.MILLISECONDS)
        assertTrue(latch.count == 0L)
    }

    @Test
    fun testDataTransformationMissionNameWithSorting() {
        val latch = CountDownLatch(3)
        val observer = Observer<Resource<List<Launch>>> {
            latch.countDown()
            if (latch.count == 0L) {
                val firstLaunchItem = it.data?.first()
                val lastLaunchItem = it.data?.last()
                assertEquals(firstLaunchItem?.launchYear, "2016")
                assertEquals(firstLaunchItem?.name, "ABS-2A / Eutelsat 117W B")
                assertEquals(lastLaunchItem?.launchYear, "2018")
                assertEquals(lastLaunchItem?.name, "ZUMA")
            }
        }
        launchViewModel.remoteLaunches.observeForever(observer)
        launchViewModel.refresh(Mode.SORT_MISSION_NAME, launches!!)
        latch.await(100, TimeUnit.MILLISECONDS)
        assertTrue(latch.count == 0L)
    }

    @Test
    fun testDataTransformationFilter() {
        val latch = CountDownLatch(3)
        val observer = Observer<Resource<List<Launch>>> {
            latch.countDown()
            if (latch.count == 0L) {
                val firstLaunchItem = it.data?.first()
                val lastLaunchItem = it.data?.last()
                assertEquals(firstLaunchItem?.launchYear, "2008")
                assertEquals(firstLaunchItem?.name, "RatSat")
                assertEquals(lastLaunchItem?.launchYear, "2021")
                assertEquals(lastLaunchItem?.name, "DART")
            }
        }
        launchViewModel.remoteLaunches.observeForever(observer)
        launchViewModel.refresh(Mode.FILTER_LAUNCH_SUCCESS, launches!!)
        latch.await(100, TimeUnit.MILLISECONDS)
        assertTrue(latch.count == 0L)
    }

    private inline fun <reified T> readJson(fileName: String): T? {
        val input = this::class.java.classLoader?.getResourceAsStream(fileName) ?: return null
        return Json { ignoreUnknownKeys = true }.decodeFromStream(input)
    }
}
