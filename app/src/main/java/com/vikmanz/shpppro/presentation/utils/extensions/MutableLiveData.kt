package com.vikmanz.shpppro.presentation.utils.extensions

import androidx.lifecycle.MutableLiveData

fun MutableLiveData<Boolean>.swapBoolean() {
    this.value = !this.value!!
}