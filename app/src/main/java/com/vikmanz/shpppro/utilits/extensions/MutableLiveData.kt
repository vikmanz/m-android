package com.vikmanz.shpppro.utilits.extensions

import androidx.lifecycle.MutableLiveData

fun MutableLiveData<Boolean>.swapBoolean() {
    this.value = !this.value!!
}