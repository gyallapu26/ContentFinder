package com.example.truecallercontentfinder.core.util

import android.view.View
import androidx.core.view.isGone
import androidx.core.view.isVisible


/**
 Since Sealed class provides the advantages of both enum and
 abstraction, here created three states for UI and wrapped the required
 response init.
 */
sealed class Result<out T> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val errorMessage: String) : Result<Nothing>()

}

fun View.visible() {
    if (!this.isVisible) this.visibility = View.VISIBLE
}

fun View.gone() {
    if (!this.isGone) this.visibility = View.GONE
}



