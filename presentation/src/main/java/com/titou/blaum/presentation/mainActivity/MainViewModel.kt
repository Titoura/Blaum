package com.titou.blaum.presentation.mainActivity

import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.ViewModel
import com.titou.blaum.use_cases.TitleRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.Observables
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import org.koin.core.KoinComponent


class MainViewModel(private val titleRepository: TitleRepository) : KoinComponent, ViewModel() {


    var scrollState: Parcelable? = null
    private val disposables = CompositeDisposable()
    var mainActivityState: BehaviorSubject<MainActivityState> = BehaviorSubject.createDefault(
        MainActivityState(
            isLoading = true,
            error = null,
            titles = emptyList()
        )
    )
    val notifyDataSetChangedTrigger: BehaviorSubject<Unit> = BehaviorSubject.createDefault(Unit)
    val searchFilterSubject: BehaviorSubject<String> = BehaviorSubject.createDefault("")

    init {
        Observables.combineLatest(
            getTitles(),
            searchFilterSubject
        )
            .map { (titles, searchInput) ->
                if (searchInput.isBlank()) {
                    titles
                } else {
                    titles.filter { it.title.contains(searchInput) }
                }

            }
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({ newTitles ->
                mainActivityState.onNext(
                    MainActivityState(
                        isLoading = false,
                        error = null,
                        titles = newTitles
                    )
                )
                notifyDataSetChangedTrigger.onNext(Unit)
            }, {
                it.printStackTrace()
                mainActivityState.onNext(
                    MainActivityState(
                        isLoading = false,
                        error = it,
                        titles = emptyList()
                    )
                )
                notifyDataSetChangedTrigger.onNext(Unit)
            })
    }

    private fun getTitles() = titleRepository.fetchAndSaveTitles().distinctUntilChanged()
    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}