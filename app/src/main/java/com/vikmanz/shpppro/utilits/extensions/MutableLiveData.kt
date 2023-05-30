package com.vikmanz.shpppro.utilits.extensions

import androidx.lifecycle.MutableLiveData

fun MutableLiveData<Boolean>.swapBoolean() {
    this.value = !this.value!!
}

fun MutableLiveData<Boolean>.isTrue(): Boolean = this.value == true
fun MutableLiveData<Boolean>.isFalse(): Boolean = this.value == false