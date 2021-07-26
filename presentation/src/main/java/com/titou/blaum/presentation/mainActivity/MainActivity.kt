package com.titou.blaum.presentation.mainActivity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.titou.blaum.presentation.R
import com.titou.blaum.presentation.databinding.ActivityMainBinding
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val disposables = CompositeDisposable()

    private val viewModel: MainViewModel by viewModel()
    lateinit var adapter: TitleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpRecyclerView()
        setUpRefreshDataTrigger()
        setUpStateUpdates()
    }

    private fun setUpRecyclerView(){
        adapter = TitleAdapter(viewModel.mainActivityState)

        binding.recyclerview.apply {
            setHasFixedSize(true)
            adapter = this@MainActivity.adapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

    }

    private fun setUpRefreshDataTrigger(){
        viewModel.notifyDataSetChangedTrigger
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({
                runOnUiThread {
                    adapter.notifyDataSetChanged()
                }
            }, {
                it.printStackTrace()
            }).addTo(disposables)
    }

    private fun setUpStateUpdates(){
        binding.state = viewModel.mainActivityState.value

        viewModel.mainActivityState.subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({ newState ->
                    binding.state = newState
            }, {
                it.printStackTrace()
            }).addTo(disposables)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val menuItem: MenuItem = menu.findItem(R.id.search_menu_item)
        val searchView = menuItem.actionView as SearchView
        searchView.maxWidth = Int.MAX_VALUE
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchFilterSubject.onNext(newText ?: "")
                return true
            }

        })
        return true
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