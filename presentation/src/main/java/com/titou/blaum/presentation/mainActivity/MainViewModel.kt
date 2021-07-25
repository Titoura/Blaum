package com.titou.blaum.presentation.mainActivity

import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.ViewModel
import com.titou.blaum.use_cases.TitleRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import org.koin.core.KoinComponent


class MainViewModel(private val titleRepository: TitleRepository) : KoinComponent, ViewModel() {

    var scrollState: Parcelable? = null
    private val disposables = CompositeDisposable()
    var mainActivityState = BehaviorSubject.createDefault(MainActivityState(isLoading = true, error = null, titles = emptyList()))
    val notifyDataSetChangedTrigger = BehaviorSubject.createDefault(Unit)

    init {
        getTitles()
            .distinctUntilChanged()
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({
                Log.e("Titou2", it.size.toString())
                mainActivityState.onNext(MainActivityState(isLoading = false, error = null, titles = it))
                notifyDataSetChangedTrigger.onNext(Unit)
            }, {
                it.printStackTrace()
                mainActivityState.onNext(MainActivityState(isLoading = false, error = it, titles = emptyList()))
                notifyDataSetChangedTrigger.onNext(Unit)
            })
    }

    private fun getTitles() = titleRepository.fetchAndSaveTitles()
    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}