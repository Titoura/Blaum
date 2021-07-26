package com.titou.blaum.data_source.local.database.dao

import androidx.room.*
import com.titou.blaum.data_source.local.database.models.TitleRoomModel
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import org.koin.core.KoinComponent

@Dao
interface TitlesDao : KoinComponent {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(titleRoomModels: List<TitleRoomModel>) : Single<Unit>

    @Query("SELECT * FROM title")
    fun getAll(): Observable<List<TitleRoomModel>>
}
