package com.example.finalplayground.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.example.finalplayground.data.network.Resource
import com.example.finalplayground.domain.model.Launch
import com.example.finalplayground.domain.usecases.SpaceXUseCase
import com.example.finalplayground.ui.model.LaunchContentHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LaunchViewModel @Inject constructor(private val useCase: SpaceXUseCase) : ViewModel() {

    private var currentMode = Mode.REFRESH

    private val launchListLiveData = MutableLiveData(LaunchContentHolder(currentMode, listOf()))

    var remoteLaunches: LiveData<Resource<List<Launch>>> = launchListLiveData.switchMap {
        if (it.mode == Mode.REFRESH) {
            fetchLaunches()
        } else {
            liveData {
                emit(performOperation(it))
            }
        }
    }

    fun refresh(mode: Mode, launches: List<Launch>) {
        this.currentMode = mode
        launchListLiveData.value = LaunchContentHolder(mode, launches)
    }

    private fun fetchLaunches() = liveData {
        emit(Resource.loading())
        emit(useCase.launches())
    }

    private fun performOperation(holder: LaunchContentHolder): Resource<List<Launch>> {
        return when (holder.mode) {
            Mode.SORT_LAUNCH_DATE -> {
                Resource.success(holder.launches.sortedBy { it.dateUnix })
            }
            Mode.SORT_MISSION_NAME -> {
                Resource.success(holder.launches.sortedBy { it.name })
            }
            Mode.FILTER_LAUNCH_SUCCESS -> {
                Resource.success(holder.launches.filter { it.success == true })
            }
            else -> {
                Resource.success(holder.launches)
            }
        }
    }

    fun mapOfLaunches(launches: List<Launch>): Map<String?, List<Launch>> {
        return when (currentMode) {
            Mode.SORT_LAUNCH_DATE -> {
                launches.groupBy({ it.launchYear }, { it })
            }
            Mode.SORT_MISSION_NAME -> {
                launches.groupBy({ it.name.first().toString() }, { it })
            } else -> {
                mapOf()
            }
        }
    }
}

enum class Mode {
    REFRESH,
    SORT_MISSION_NAME,
    SORT_LAUNCH_DATE,
    FILTER_LAUNCH_SUCCESS
}
