package com.vikmanz.shpppro.data.utils

import com.github.javafaker.Faker

object RandomPhotoTaker {

    private val faker = Faker.instance() // fake data generator.

    fun getRandomPhoto() = faker.avatar().image()



}