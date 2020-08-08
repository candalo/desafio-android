package com.picpay.desafio.android.view

import android.view.View
import android.widget.ProgressBar

fun ProgressBar.visible() {
    visibility = View.VISIBLE
}

fun ProgressBar.gone() {
    visibility = View.GONE
}