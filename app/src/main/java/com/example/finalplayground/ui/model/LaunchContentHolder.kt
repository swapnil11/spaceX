package com.example.finalplayground.ui.model

import com.example.finalplayground.domain.model.Launch
import com.example.finalplayground.ui.viewmodel.Mode

data class LaunchContentHolder(
    val mode: Mode,
    val launches: List<Launch>
)
