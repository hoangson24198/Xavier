package com.hoangson.xavier.core.extension

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.bumptech.glide.request.RequestOptions

fun ImageView.load(url: String?) {
    url?.let {
        if (it.trim().isNotEmpty()) {
            Glide.with(this).load(it).into(this)
        }
    }
}

fun ImageView.loadCircular(url: String?) {
    url?.let {
        if (it.trim().isNotEmpty()) {
            Glide.with(this).load(it).apply(RequestOptions().transform(CircleCrop())).into(this)
        }
    }
}
