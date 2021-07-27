package com.titou.blaum.presentation.mainActivity

import android.os.Parcelable
import android.util.Log
import androidx.lifecycle.ViewModel
import com.titou.blaum.presentation.utils.Optional
import com.titou.blaum.use_cases.TitleRepository
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.Observables
import io.reactivex.rxjava3.kotlin.addTo
import io.reactivex.rxjava3.schedulers.Schedulers
import io.reactivex.rxjava3.subjects.BehaviorSubject
import org.koin.core.KoinComponent


class MainViewModel(private val titleRepository: TitleRepository) : KoinComponent, ViewModel() {


    // Lifecycle related
    var scrollState: Parcelable? = null
    private val disposables = CompositeDisposable()

    // Data subjects
    var mainActivityState: BehaviorSubject<MainActivityState> =
        BehaviorSubject.createDefault(MainActivityState.loading())
    val searchFilterSubject: BehaviorSubject<String> = BehaviorSubject.createDefault("")
    var fetchingErrorSubject: BehaviorSubject<Optional<Throwable>> =
        BehaviorSubject.createDefault(Optional(null))

    // Triggers
    val notifyDataSetChangedTrigger: BehaviorSubject<Unit> = BehaviorSubject.createDefault(Unit)
    val onTriggerDataRefresh: BehaviorSubject<Unit> = BehaviorSubject.createDefault(Unit)


    init {


        // Handle new data received
        Observables.combineLatest(
            getTitles(),
            searchFilterSubject,
            fetchingErrorSubject
        )
            .map { (titles, searchInput) ->
                if (searchInput.isBlank()) {
                    titles
                } else {
                    titles.filter { it.title.contains(searchInput) }
                }
            }
            .flatMap({
                fetchingErrorSubject
            }, { titles, error ->
                Pair(titles, error)
            })
            .observeOn(Schedulers.io())
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({ (newTitles, error) ->
                Log.e("Titou", "combineLatest success")

                if (error.value == null) {
                    mainActivityState.onNext(
                        MainActivityState.success(newTitles)
                    )
                } else {
                    mainActivityState.onNext(
                        MainActivityState.error(error.value)
                    )
                }
                notifyDataSetChangedTrigger.onNext(Unit)
            }, {
                it.printStackTrace()
                mainActivityState.onNext(
                    MainActivityState.error(it)
                )
                notifyDataSetChangedTrigger.onNext(Unit)
            }).addTo(disposables)


        //Handle remote data fetching
        onTriggerDataRefresh.doOnNext {
            mainActivityState.onNext(
                MainActivityState.loading()
            )
            notifyDataSetChangedTrigger.onNext(Unit)
        }.doOnNext {
            refreshData()
        }.subscribe({}, {
            it.printStackTrace()
            mainActivityState.onNext(
                MainActivityState.error()
            )
        }).addTo(disposables)
    }


    private fun getTitles() = titleRepository.getLocalTitles()
    private fun refreshData() {
        titleRepository.fetchAndSaveTitles().doOnError {
        }.subscribe({
            fetchingErrorSubject.onNext(Optional(null))
        }, {
            fetchingErrorSubject.onNext(Optional(it))
        }).addTo(disposables)
    }


    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}