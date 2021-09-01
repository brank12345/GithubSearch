package com.example.githubsearch.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

inline fun <T> AppCompatActivity.observe(data: LiveData<T>, crossinline action: (T?) -> Unit) {
    data.observe(this, Observer { action(it) })
}