package com.titou.blaum.presentation.mainActivity

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.titou.blaum.data.entity.Title
import com.titou.blaum.presentation.databinding.ItemTitleBinding
import com.titou.blaum.presentation.utils.loadWithHeader
import io.reactivex.rxjava3.subjects.BehaviorSubject
import org.koin.core.KoinComponent
import org.koin.core.inject


class TitleAdapter(titlesSubject: BehaviorSubject<MainActivityState>) :
    RecyclerView.Adapter<TitleAdapter.TitleViewHolder>(), KoinComponent {

    private val context: Context by inject()

    private val titles = mutableListOf<Title>()

    init {
        //Todo : create function to remove and add directly in the mutablelist
        titlesSubject.subscribe({ newState ->
            titles.clear()
            titles.addAll(newState.titles)
        }, {
            it.printStackTrace()
        })
    }

    class TitleViewHolder(private val binding: ItemTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context, title: Title) {
            binding.title = title
            binding.executePendingBindings()

            Glide
                .with(context)
                .loadWithHeader(title.thumbnailUrl)
                .into(binding.thumbnailImageview)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleViewHolder {
        val binding = ItemTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TitleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TitleViewHolder, position: Int) {
        holder.bind(context, titles[position])

    }

    override fun getItemCount() = titles.size


}
