package com.titou.blaum.presentation.utils

import android.content.Context
import android.os.Build
import androidx.annotation.ColorRes

fun getColor(context : Context, @ColorRes colorRes: Int) : Int{
    return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
        context.resources.getColor(colorRes, context.theme)
    } else {
        context.resources.getColor(colorRes)
    }
}