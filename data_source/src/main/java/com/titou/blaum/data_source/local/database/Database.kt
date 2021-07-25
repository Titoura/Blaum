package com.titou.blaum.data_source.local.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import com.titou.blaum.data_source.local.database.dao.AlbumDao
import com.titou.blaum.data_source.local.database.dao.TitlesDao
import com.titou.blaum.data_source.local.database.models.AlbumRoomModel
import com.titou.blaum.data_source.local.database.models.TitleRoomModel


const val DB_VERSION = 1
const val DB_NAME = "Blaum.db"

@androidx.room.Database(entities = [TitleRoomModel::class, AlbumRoomModel::class], version = DB_VERSION)
abstract class Database : RoomDatabase() {
    abstract fun albumRoomModelDao(): AlbumDao
    abstract fun titlesRoomModelDao(): TitlesDao

    companion object {
        @Volatile
        private var databseInstance: Database? = null

        fun getDatabaseInstance(mContext: Context): Database =
            databseInstance ?: synchronized(this) {
                databseInstance ?: buildDatabaseInstance(mContext).also {
                    databseInstance = it
                }
            }

        private fun buildDatabaseInstance(mContext: Context) =
            Room.databaseBuilder(mContext, Database::class.java, DB_NAME)
                .fallbackToDestructiveMigration()
                .build()

    }
}