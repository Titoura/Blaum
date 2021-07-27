package com.titou.blaum.presentation.mainActivity

import com.titou.blaum.entities.Title


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

    override fun hashCode(): Int {
        var result = isLoading.hashCode()
        result = 31 * result + (error?.hashCode() ?: 0)
        result = 31 * result + titles.hashCode()
        return result
    }


    companion object{
        fun empty() = MainActivityState(false, null, emptyList())
        fun error(throwable: Throwable? = null) = MainActivityState(false, throwable, emptyList())
        fun success(titles : List<Title>) = MainActivityState(false, null, titles)
        fun loading() = MainActivityState(true, null, emptyList())

    }
}
