package com.titou.blaum.data_source.di

import androidx.room.Room
import com.titou.blaum.data_source.local.database.Database
import com.titou.blaum.data_source.local.database.DatabaseTitlesSourceImpl
import com.titou.blaum.use_cases.DatabaseTitlesSource
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.bind
import org.koin.dsl.module

object DatabaseModule {
    val module = module {

        single { get<Database>().albumRoomModelDao() }

        single {
            Room.databaseBuilder(androidApplication(), Database::class.java, "blaum.db")
                .build()
        }

        single { DatabaseTitlesSourceImpl(get()) } bind (DatabaseTitlesSource::class)
    }
}