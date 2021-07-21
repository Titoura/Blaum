package com.titou.blaum.data_source.local.database.dao

import androidx.room.*
import com.titou.blaum.data_source.local.database.models.AlbumRoomModel
import com.titou.blaum.data_source.local.database.models.AlbumWithTitlesRoomModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.koin.core.KoinComponent

@Dao
interface AlbumDao : KoinComponent {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg albumRoomModel: AlbumRoomModel) : Single<Unit>

    @Query("SELECT * FROM album")
    fun getAll(): Observable<List<AlbumRoomModel>>

    @Transaction
    @Query("SELECT * FROM album")
    fun getAlbumsWithTitles(): List<AlbumWithTitlesRoomModel>

}
