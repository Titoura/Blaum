package com.titou.blaum.presentation.utils

import android.widget.ImageView
import com.titou.blaum.entities.Title

interface CellClickListener {
    fun onCellClickListener(title: Title, imageView: ImageView)
}
