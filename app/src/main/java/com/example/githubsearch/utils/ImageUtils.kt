package com.example.githubsearch.utils

import android.widget.ImageView
import com.example.githubsearch.R
import com.squareup.picasso.Picasso

fun ImageView.setAvatarUrl(imageUrl: String) {
    if (imageUrl.isNotEmpty()) {
        Picasso.get()
            .load(imageUrl)
            .fit()
            .centerCrop()
            .transform(CircleTransform())
            .placeholder(R.mipmap.ic_launcher_round)
            .into(this)
    } else {
        setImageResource(R.mipmap.ic_launcher_round)
    }
}