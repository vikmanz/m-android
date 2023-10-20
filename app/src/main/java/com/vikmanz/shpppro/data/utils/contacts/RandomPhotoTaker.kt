package com.vikmanz.shpppro.data.utils.contacts

import com.github.javafaker.Faker

object RandomPhotoTaker {

    private val faker = Faker.instance() // fake data generator.

    fun getRandomPhoto(): String = faker.avatar().image()

}