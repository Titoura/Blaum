package com.titou.blaum.presentation.titlesList

import android.R.attr.radius
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.titou.blaum.entities.Title
import com.titou.blaum.presentation.databinding.ItemTitleBinding
import com.titou.blaum.presentation.mainActivity.MainActivityState
import com.titou.blaum.presentation.utils.CellClickListener
import com.titou.blaum.presentation.utils.loadWithHeader
import io.reactivex.rxjava3.subjects.BehaviorSubject
import org.koin.core.KoinComponent
import org.koin.core.inject


class TitleAdapter(
    titlesSubject: BehaviorSubject<MainActivityState>,
    private val cellClickListener: CellClickListener
) :
    RecyclerView.Adapter<TitleAdapter.TitleViewHolder>(), KoinComponent {

    private val context: Context by inject()

    private val titles = mutableListOf<Title>()

    init {
        titlesSubject.subscribe({ newState ->
            titles.clear()
            titles.addAll(newState.titles)
        }, {
            it.printStackTrace()
        })
    }

    class TitleViewHolder(val binding: ItemTitleBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context, title: Title) {
            binding.title = title
            binding.executePendingBindings()

            //TODO : find out why placeholder is not visible
            val circularProgressDrawable = CircularProgressDrawable(context)
            circularProgressDrawable.strokeWidth = 2F
            circularProgressDrawable.centerRadius = radius.toFloat()
            circularProgressDrawable.start()

            Glide
                .with(context)
                .loadWithHeader(title.thumbnailUrl)
                .placeholder(circularProgressDrawable)
                .into(binding.thumbnailImageview)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TitleViewHolder {
        val binding = ItemTitleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TitleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TitleViewHolder, position: Int) {
        val title = titles[position]
        holder.bind(context, title)

        holder.itemView.setOnClickListener {
            cellClickListener.onCellClickListener(title, holder.binding.thumbnailImageview)
        }

    }

    override fun getItemCount() = titles.size


}
