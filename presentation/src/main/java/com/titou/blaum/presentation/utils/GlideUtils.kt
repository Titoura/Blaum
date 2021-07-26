package com.titou.blaum.presentation.utils

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
