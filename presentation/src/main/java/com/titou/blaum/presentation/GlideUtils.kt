package com.titou.blaum.presentation

import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.model.GlideUrl
import com.bumptech.glide.load.model.LazyHeaders


fun RequestManager.loadWithHeader(url: String) = load(
    GlideUrl(
        url, LazyHeaders.Builder()
            .addHeader("User-Agent", "your-user-agent")
            .build()
    )
)
