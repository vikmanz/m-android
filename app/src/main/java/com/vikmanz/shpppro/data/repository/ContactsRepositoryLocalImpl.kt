package com.vikmanz.shpppro.data.repository

import android.net.Uri
import com.github.javafaker.Faker
import com.vikmanz.shpppro.common.Constants.START_NUMBER_OF_CONTACTS
import com.vikmanz.shpppro.common.extensions.log
import com.vikmanz.shpppro.common.model.Contact
import com.vikmanz.shpppro.common.model.ContactItem
import com.vikmanz.shpppro.data.utils.ContactsPhoneInfoTaker
import com.vikmanz.shpppro.domain.repository.ContactsRepositoryLocal
import com.vikmanz.shpppro.domain.usecases.contacts.GetAllUsersUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import java.util.UUID
import javax.inject.Inject

/**
 * Implementation of repository.
 * Main service to create contacts objects from information on from random.
 */
class ContactsRepositoryLocalImpl @Inject constructor(
    private val getAllUsersUseCase: GetAllUsersUseCase
) : ContactsRepositoryLocal {

    //This object is a wrapper. if we pass it a new object it will call emit
    private val _contactList = MutableStateFlow(listOf<ContactItem>())

    //this object sends out the immutable list
    override val contactList: StateFlow<List<ContactItem>> = _contactList.asStateFlow()

    private val faker = Faker.instance() // fake data generator.

    private var imgCounter = 0  // counter to switch random images.

    private val multiselectList = ArrayList<ContactItem>()

    private val coroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob())

    init {
        //todo
        coroutineScope.launch {
            //setFakeContacts()



        }
    }

    /**
     * Create and return one contact with information from input.
     */
    override fun createContact(
        contactPhotoLink: Any,
        photoIndex: Int,
        name: String,
        career: String,
        email: String,
        phone: String,
        address: String,
        birthday: String
    ): ContactItem {
        val newContact = ContactItem(
            Contact(
                contactId = getRandomId(),
                contactPhotoLink = contactPhotoLink,
                contactPhotoIndex = photoIndex,
                contactName = name,
                contactCareer = career,
                contactEmail = email,
                contactPhone = phone,
                contactAddress = address,
                contactBirthday = birthday
            )
        )
        imgCounter++
        return newContact
    }

    /**
     * Create and return one contact with random fake information.
     */
    //todo private
    override fun generateRandomContact(): ContactItem =
        createContact(
            contactPhotoLink = IMAGES[imgCounter % IMAGES.size],
            photoIndex = imgCounter,
            name = faker.name().fullName(),
            career = faker.company().name(),
            email = faker.internet().emailAddress(),
            phone = faker.phoneNumber().phoneNumber(),
            address = faker.address().fullAddress(),
            birthday = faker.date().birthday().toString(),
        )

    /**
     * Create and return list of fake contacts.
     */
    override suspend fun setFakeContacts() {
        withContext(Dispatchers.IO) {
            _contactList.value = (0 until START_NUMBER_OF_CONTACTS).map {
                generateRandomContact()
            }.toMutableList()
        }
    }

    /**
     * Create and return contact list with information from phonebook ArrayList<[name: String, phone: String]>.
     */
    override fun setPhoneContacts() {
        val listOfContactsInformation = ContactsPhoneInfoTaker().getPhonebookContactsInfo()
        _contactList.value = (0 until listOfContactsInformation.size).map {
            createContact(
                contactPhotoLink = Uri.parse(listOfContactsInformation[it][1]).let { uri ->
                    if (uri == Uri.EMPTY) IMAGES[imgCounter % IMAGES.size] else uri
                },
                photoIndex = imgCounter,
                name = listOfContactsInformation[it][0],
                career = listOfContactsInformation[it][4],
                email = listOfContactsInformation[it][3],
                phone = listOfContactsInformation[it][2],
                address = faker.address().fullAddress(),
                birthday = faker.date().birthday().toString()
            )
        }.toMutableList()
    }

    /**
     * Return random id for new contacts.
     */
    private fun getRandomId(): Long {
        return UUID.randomUUID().mostSignificantBits
    }

    /**
     * Return current random photo url.
     */
    override fun getCurrentContactPhotoUrl(): URL {
        return IMAGES[imgCounter % IMAGES.size]
    }

    /**
     * Return current random photo counter.
     */
    override fun getCurrentPhotoCounter(): Int {
        return imgCounter
    }

    /**
     * Change random photo counter to switch random image to next.
     */
    override fun incrementPhotoCounter() {
        imgCounter++
    }

    override fun addContact(contact: ContactItem) {
        addContact(contact, _contactList.value.size)
    }

    /**
     * Add new contact to list of contacts to concrete index.
     */
    override fun addContact(contact: ContactItem, index: Int) {
        _contactList.value = _contactList.value.toMutableList().apply { add(index, contact) }
    }

    /**
     * Delete contact from list of contacts.
     */
    override fun deleteContact(contact: ContactItem) {
        _contactList.value = _contactList.value.toMutableList().apply { remove(contact) }
    }

    /**
     * Get contact position in list of contacts.
     */
    override fun getContactPosition(contact: ContactItem): Int {
        _contactList.value.indexOf(contact).let { if (it != -1) return it }
        return 0
    }

    /**
     * Get contact from list via index.
     */
    override fun getContact(index: Int): ContactItem? =
        if (index >= 0 && index < _contactList.value.size) _contactList.value[index]
        else null

    /**
     * Get contact from list via id.
     */
    override fun findContact(contactId: Long): ContactItem? {
        _contactList.value.forEach { if (it.contact.contactId == contactId) return it }
        return null
    }


    override fun deleteMultipleContacts() {
        multiselectList.forEach { contact ->
            _contactList.value = _contactList.value.toMutableList().apply {
                remove(contact)
            }
        }
        multiselectList.clear()
    }

    override fun clearMultiselect() {
        log("clear multiselect")
        _contactList.value = _contactList.value.toMutableList().onEach { it.isChecked = false }
        multiselectList.clear()
    }

    override fun checkIsMultiselect(): Boolean {
        _contactList.value.forEach {
            if (it.isChecked) {
                log("check is multiselect - true")
                return true
            }
        }
        log("check is multiselect - false")
        return false
    }

    override fun toggleContactSelectionState(contact: ContactItem) {
        log("Toggle contact state")
        changeContactSelectionState(contact)
        updateMultiselectContactList(contact)
        log("arraylist size = ${multiselectList.size}")
    }


    private fun updateMultiselectContactList(contact: ContactItem) {
        val index = _contactList.value.indexOf(contact)
        if (_contactList.value[index].isChecked) {
            multiselectList.add(contact)
        } else {
            multiselectList.remove(contact)
        }
    }

    private fun changeContactSelectionState(contact: ContactItem) {
        val index = _contactList.value.indexOf(contact)
        if (index != -1) _contactList.value[index].apply {
            isChecked = !isChecked
        }
    }

    override fun checkMultiselectState(): Boolean = multiselectList.size != 0


    override fun isContainsContact(contact: ContactItem): Boolean {
        return _contactList.value.contains(contact)
    }


    /**
     * Random images for fake data or adding new contacts.
     */
    companion object {
        val IMAGES = mutableListOf(
            URL("https://images.unsplash.com/photo-1600267185393-e158a98703de?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0NjQ0&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800"),
            URL("https://images.unsplash.com/photo-1579710039144-85d6bdffddc9?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0Njk1&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800"),
            URL("https://images.unsplash.com/photo-1488426862026-3ee34a7d66df?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0ODE0&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800"),
            URL("https://images.unsplash.com/photo-1620252655460-080dbec533ca?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0NzQ1&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800"),
            URL("https://images.unsplash.com/photo-1613679074971-91fc27180061?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0NzUz&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800"),
            URL("https://images.unsplash.com/photo-1485795959911-ea5ebf41b6ae?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0NzU4&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800"),
            URL("https://images.unsplash.com/photo-1545996124-0501ebae84d0?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0NzY1&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800"),
            URL("https://images.unsplash.com/photo-1567186937675-a5131c8a89ea?crop=entropy&cs=tinysrgb&fit=crop&fm=jpg&h=600&ixid=MnwxfDB8MXxyYW5kb218fHx8fHx8fHwxNjI0MDE0ODYx&ixlib=rb-1.2.1&q=80&utm_campaign=api-credit&utm_medium=referral&utm_source=unsplash_source&w=800")
        )
    }

}