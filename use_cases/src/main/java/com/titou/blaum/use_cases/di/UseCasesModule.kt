package com.titou.blaum.use_cases.di

import com.titou.blaum.use_cases.AlbumRepository
import org.koin.dsl.module

object UseCasesModule {
    val module = module {
        single { AlbumRepository(get(), get()) }
    }
}
