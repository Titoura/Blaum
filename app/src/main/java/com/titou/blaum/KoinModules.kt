package com.titou.blaum

import com.titou.blaum.data_source.di.DatabaseModule
import com.titou.blaum.data_source.di.TitlesApiModule
import com.titou.blaum.presentation.di.ActivityModule

object KoinModules {

    fun toList() = listOf(
        ActivityModule.module,
        DatabaseModule.module,
        TitlesApiModule.module
        )
}
