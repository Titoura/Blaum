package com.titou.blaum.use_cases.di

import com.titou.blaum.use_cases.TitleRepository
import org.koin.dsl.module

object UseCasesModule {
    val module = module {
        single { TitleRepository(get(), get()) }
    }
}
