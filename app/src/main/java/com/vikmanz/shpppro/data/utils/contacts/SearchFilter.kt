package com.vikmanz.shpppro.data.utils.contacts

import com.vikmanz.shpppro.data.model.contact_item.interfaces.ContactItemInterface

object SearchFilter {

    fun filter(list: List<ContactItemInterface>, query: String): List<ContactItemInterface> {
        val filteredList = mutableListOf<ContactItemInterface>()

        if (query.isEmpty()) {
            filteredList.addAll(list)
        } else {
            for (item in list) {
                if (item.contact.email?.contains(query) == true) {
                    filteredList.add(item)
                }
            }
        }

        return filteredList
    }


}