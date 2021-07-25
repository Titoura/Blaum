package com.titou.blaum.presentation.mainActivity

import com.titou.blaum.data.entity.Title


data class MainActivityState(
    val isLoading: Boolean,
    val error: Throwable?,
    val titles: List<Title>
) {
    override fun equals(other: Any?) : Boolean {
        if( other is MainActivityState) {
            return isLoading == other.isLoading && error == other.error && titles == other.titles
        }
        return super.equals(other)
    }
}
