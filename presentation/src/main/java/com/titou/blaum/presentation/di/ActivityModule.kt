package com.titou.blaum.presentation.di

import com.titou.blaum.presentation.mainActivity.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

object ActivityModule {
    val module = module {
        viewModel { MainViewModel(get()) }
    }
}
