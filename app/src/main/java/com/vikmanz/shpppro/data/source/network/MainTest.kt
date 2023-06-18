package com.vikmanz.shpppro.data.source.network

import com.squareup.moshi.Moshi
import com.vikmanz.shpppro.data.model.User
import com.vikmanz.shpppro.data.source.network.entities.ContactAddRequestEntity
import com.vikmanz.shpppro.data.source.network.entities.ContactAddResponseBody
import com.vikmanz.shpppro.data.source.network.entities.ContactAddResponseEntity
import com.vikmanz.shpppro.data.source.network.entities.ContactDeleteResponseBody
import com.vikmanz.shpppro.data.source.network.entities.ContactDeleteResponseEntity
import com.vikmanz.shpppro.data.source.network.entities.UserAuthorizeRequestEntity
import com.vikmanz.shpppro.data.source.network.entities.UserEditRequestEntity
import com.vikmanz.shpppro.data.source.network.interfaces.ShPPApi
import kotlinx.coroutines.runBlocking
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

const val BASE_URL = "http://178.63.9.114:7777/api/"
const val EMAIL = "boris.lezo@gmail.com"
const val PASS = "passwordA@3"

fun main() = runBlocking {
    print(Integer.parseInt(""))

    @Suppress("UNUSED_VARIABLE") val loggingInterface = HttpLoggingInterceptor()
        .setLevel(HttpLoggingInterceptor.Level.BODY)
    val client = OkHttpClient.Builder()
        //.addInterceptor(loggingInterface)
        .build()
    val moshi = Moshi.Builder().build()
    val moshiConvertorFactory = MoshiConverterFactory.create(moshi)
    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(moshiConvertorFactory)
        .build()
    val api = retrofit.create(ShPPApi::class.java)
    val login = UserAuthorizeRequestEntity(
        email = EMAIL,
        password = PASS
    )


    // LOGIN
    println("Login...")
    val responseLogin = api.authorizeUser(login)
    println("...done!")
    println("Token = ${responseLogin.data.accessToken}")
    println("User name = ${responseLogin.data.user.name}")
    println("\n")



    // REFRESH
    print("Refresh token...")
    val responseRefreshedToken = api.refreshToken(responseLogin.data.refreshToken)
    println("...done!")
    println("Access token = ${responseRefreshedToken.data.accessToken}")
    println("Refreshed token = ${responseRefreshedToken.data.refreshToken}")
    println("\n")

    // ! TOKEN & ID !
    val token = "Bearer " + responseRefreshedToken.data.accessToken
    val selfId = 208



    // GET USER
    print("Get user...")
    val responseGetSelfContact = api.getUser(token, selfId)
    println("...done!")
    println("Self:")
    printUser(responseGetSelfContact.data.user)



    // EDIT USER
    print("Change user phone and career...")
    val responseChangeUserInfo = api.editUser(token, selfId, UserEditRequestEntity(
        phone = "32!",
        career = "duje super klassna robota"
    )
    )
    println("...done!")
    println("Self from response:")
    printUser(responseChangeUserInfo.data.user)
    print("Get user again...")
    val responseSelfContactAgain = api.getUser(token, selfId)
    println("...done!")
    println("Self again:")
    printUser(responseSelfContactAgain.data.user)


    // GET ALL USERS
    print("Get All users...")
    val responseGetAllUsers = api.getAllUsers(token)
    println("...done!")
    println("========== Print All users: ==========")
    responseGetAllUsers.data.users.forEach { printUser(it) }
    println("========== DONE ==========\n")


    // GET USER CONTACTS
    print("Get user contacts...")
    val responseGetUserContacts = api.getUserContacts(token, selfId)
    println("...done!")
    println("========== Print user contacts: ==========")
    responseGetUserContacts.data.contacts.forEach { printUser(it) }
    println("========== DONE ==========\n")


    // ADD CONTACTS
    print("Add user contacts...")
    val addingContacts = listOf(207, 206, 202, 126, 114)
    var responseAddUserContacts =
        ContactAddResponseEntity("", 0, "",  ContactAddResponseBody(emptyList()))
    addingContacts.forEach {
        responseAddUserContacts = api.addContact(token, selfId, ContactAddRequestEntity(it))
    }
    println("...done!")
    println("========== Print user contacts after adding: ==========")
    responseAddUserContacts.data.contacts.forEach { printUser(it) }
    println("========== DONE ==========\n")


    // DELETE CONTACTS
    print("Delete user contacts...")
    val deletingContacts = listOf(207, 126)
    var responseDeleteUserContacts =
        ContactDeleteResponseEntity("", 0, "",  ContactDeleteResponseBody(emptyList()))
    deletingContacts.forEach {
        responseDeleteUserContacts = api.deleteContact(token, selfId, it)
    }
    println("...done!")
    println("========== Print user contacts after delete: ==========")
    responseDeleteUserContacts.data.contacts.forEach { printUser(it) }
    println("========== DONE ==========\n")

}

fun printUser(user: User) =
    with (user){
        println(    "" +
                "user = $id : $email \n" +
                if (name != null) "\t name = $name \n" else "" +
                if (phone != null) "\t phone = $phone \n" else "" +
                if (address != null) "\t address = $address \n" else "" +
                if (career != null) "\t career = $career \n" else "" +
                if (birthday != null) "\t birthday = $birthday \n" else "" +
                if (facebook != null) "\t facebook = $facebook \n" else "" +
                if (instagram != null) "\t instagram = $instagram \n" else "" +
                if (twitter != null) "\t twitter = $twitter \n" else "" +
                if (linkedin != null) "\t linkedin = $linkedin \n" else "" +
                if (image != null) "\t image = $image \n" else ""
        )
    }
