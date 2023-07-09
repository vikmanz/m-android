package com.vikmanz.shpppro.data

import com.vikmanz.shpppro.common.model.User

const val BASE_URL = "http://178.63.9.114:7777/api/"
const val EMAIL = "boris.lezo@gmail.com"
const val PASS = "passwordA@3"

//fun main() = runBlocking {
//    print(Integer.parseInt(""))
//
//    val api = ""
//    val login = UserAuthorizeRequest(
//        email = EMAIL,
//        password = PASS
//    )

//    // GET ALL USERS
//    print("Get All users...")
//    val responseGetAllUsers = api.getAllUsers(token)
//    println("...done!")
//    println("========== Print All users: ==========")
//    responseGetAllUsers.data.users.forEach { printUser(it) }
//    println("========== DONE ==========\n")

//
//    // ADD CONTACTS
//    print("Add user contacts...")
//    val addingContacts = listOf(207, 206, 202, 126, 114)
//    var responseAddUserContacts =
//        ContactAddResponse("", 0, "",  ContactAddResponseBody(emptyList()))
//    addingContacts.forEach {
//        responseAddUserContacts = api.addContact(token, selfId, ContactAddRequest(it))
//    }
//    println("...done!")
//    println("========== Print user contacts after adding: ==========")
//    responseAddUserContacts.data.contacts.forEach { printUser(it) }
//    println("========== DONE ==========\n")
//
//
//    // DELETE CONTACTS
//    print("Delete user contacts...")
//    val deletingContacts = listOf(207, 126)
//    var responseDeleteUserContacts =
//        ContactDeleteResponse("", 0, "",  ContactDeleteResponseBody(emptyList()))
//    deletingContacts.forEach {
//        responseDeleteUserContacts = api.deleteContact(token, selfId, it)
//    }
//    println("...done!")
//    println("========== Print user contacts after delete: ==========")
//    responseDeleteUserContacts.data.contacts.forEach { printUser(it) }
//    println("========== DONE ==========\n")
//
//}

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
