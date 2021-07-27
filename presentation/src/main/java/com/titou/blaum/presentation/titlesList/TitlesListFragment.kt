package com.titou.blaum.presentation.titlesList

import android.os.Bundle
import android.view.*
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.titou.blaum.entities.Title
import com.titou.blaum.presentation.R
import com.titou.blaum.presentation.databinding.FragmentTitlesListBinding
import com.titou.blaum.presentation.mainActivity.MainViewModel
import com.titou.blaum.presentation.utils.CellClickListener
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.kotlin.addTo
import org.koin.androidx.viewmodel.ext.android.sharedViewModel


class TitlesListFragment : Fragment(), CellClickListener {
    private var _binding: FragmentTitlesListBinding? = null

    // This property is only valid between onCreateView and
// onDestroyView.
    private val binding get() = _binding!!

    private val disposables = CompositeDisposable()

    private val viewModel: MainViewModel by sharedViewModel()
    lateinit var adapter: TitleAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentTitlesListBinding.inflate(layoutInflater)

        postponeEnterTransition()
        setUpRecyclerView()
        setUpRefreshDataTrigger()
        setUpStateUpdates()

        binding.retryButton.setOnClickListener {
            viewModel.onTriggerDataRefresh.onNext(Unit)
        }

        return binding.root
    }


    private fun setUpRecyclerView() {
        adapter = TitleAdapter(viewModel.mainActivityState, this)

        binding.recyclerview.apply {
            setHasFixedSize(true)
            adapter = this@TitlesListFragment.adapter
            layoutManager = LinearLayoutManager(requireActivity())
        }

    }

    private fun setUpRefreshDataTrigger() {
        viewModel.notifyDataSetChangedTrigger
            .subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({
                requireActivity().runOnUiThread {
                    adapter.notifyDataSetChanged()
                }
            }, {
                it.printStackTrace()
            }).addTo(disposables)
    }

    private fun setUpStateUpdates() {
        binding.state = viewModel.mainActivityState.value

        viewModel.mainActivityState.subscribeOn(AndroidSchedulers.mainThread())
            .subscribe({ newState ->
                binding.state = newState
                startPostponedEnterTransition()
            }, {
                it.printStackTrace()
            }).addTo(disposables)
    }

    override fun onPause() {
        super.onPause()
        viewModel.scrollState = binding.recyclerview.layoutManager?.onSaveInstanceState()
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        menu.clear()
        requireActivity().menuInflater.inflate(R.menu.main_menu, menu)

        val menuItem: MenuItem = menu.findItem(R.id.search_menu_item)
        val searchView = menuItem.actionView as SearchView?
        searchView?.maxWidth = Int.MAX_VALUE
        searchView?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.searchFilterSubject.onNext(newText ?: "")
                return true
            }

        })
        super.onPrepareOptionsMenu(menu)
    }


    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }

    private fun navigateToDetails(title: Title, imageView: ImageView) {
        val directions =
            TitlesListFragmentDirections.actionTitlesListFragmentToTitleDetailsFragment(
                title,
                imageView.transitionName
            )
        val extras = FragmentNavigatorExtras(
            imageView to imageView.transitionName
        )


        findNavController().navigate(directions, extras)
    }

    override fun onCellClickListener(title: Title, imageView: ImageView) {
        navigateToDetails(title, imageView)
    }
}