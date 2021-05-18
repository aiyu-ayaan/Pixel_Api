package com.aatech.wallpaper.utlis

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun makeSnackBar(m0: String, v0: View) {
    Snackbar.make(v0, m0, Snackbar.LENGTH_SHORT).show()
}