package com.example.finalplayground.ui.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.switchMap
import com.example.finalplayground.data.network.Resource
import com.example.finalplayground.domain.model.Launch
import com.example.finalplayground.domain.usecases.SpaceXUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LaunchDetailViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val useCase: SpaceXUseCase
) : ViewModel() {

    private val launchItem = savedStateHandle.getLiveData<Launch>("item")

    val rocketDetails = launchItem.switchMap { launchItem ->
        liveData {
            emit(Resource.loading())
            emit(useCase.oneRocket(launchItem.rocket))
        }
    }
}
