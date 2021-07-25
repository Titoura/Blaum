package com.titou.blaum.presentation.mainActivity

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.titou.blaum.presentation.databinding.ActivityMainBinding
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val disposables = CompositeDisposable()
//    private val adapter: TitleAdapter by inject { parametersOf(viewModel.mainActivityState) }
    private val viewModel: MainViewModel by viewModel()
    lateinit var adapter: TitleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = TitleAdapter(viewModel.mainActivityState)

        binding.recyclerview.apply {
            setHasFixedSize(true)
            adapter = this@MainActivity.adapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        viewModel.notifyDataSetChangedTrigger.subscribe({
            Log.e("Titou", "notifyDataSetChangedTrigger")
            adapter.notifyDataSetChanged()

            Log.e("Titou", "${adapter.itemCount} items")
            Log.e("Titou", "${viewModel.mainActivityState.value.titles.size} titles")

        }, {}).addTo(disposables)
    }

    override fun onPause() {
        super.onPause()
        viewModel.scrollState = binding.recyclerview.layoutManager?.onSaveInstanceState()
    }

    override fun onResume() {
        super.onResume()
        binding.recyclerview.layoutManager?.onRestoreInstanceState(viewModel.scrollState)
    }

    override fun onDestroy() {
        disposables.dispose()
        super.onDestroy()
    }
}