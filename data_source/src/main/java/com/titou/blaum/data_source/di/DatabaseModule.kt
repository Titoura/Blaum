package com.titou.blaum.data_source.di

import androidx.room.Room
import com.titou.blaum.data_source.local.database.Database
import com.titou.blaum.data_source.local.database.DatabaseTitlesDataSourceImpl
import com.titou.blaum.use_cases.DatabaseTitlesDataSource
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.bind
import org.koin.dsl.module

object DatabaseModule {
    val module = module {

        single { get<Database>().albumRoomModelDao() }
        single { get<Database>().titlesRoomModelDao() }

        single {
            Room.databaseBuilder(androidApplication(), Database::class.java, "blaum.db").build()
        }

        single { DatabaseTitlesDataSourceImpl(get()) } bind (DatabaseTitlesDataSource::class)
    }
}