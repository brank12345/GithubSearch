package com.example.githubsearch.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager

fun closeKeyboard(context: Context?, view: View?) {
    if (context != null && view != null) {
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}