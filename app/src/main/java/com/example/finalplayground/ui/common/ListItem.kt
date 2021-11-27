package com.example.finalplayground.ui.common

import com.example.finalplayground.domain.model.Launch

sealed class ListItem {
    abstract val viewType: Int

    companion object {
        const val TYPE_DATE_ITEM = 1
        const val TYPE_LAUNCH_ITEM = 2
    }
}

data class DateItem(
    val groupHeader: String?,
    override val viewType: Int = TYPE_DATE_ITEM
) : ListItem()

data class LaunchItem(
    val launch: Launch,
    override val viewType: Int = TYPE_LAUNCH_ITEM
) : ListItem()
