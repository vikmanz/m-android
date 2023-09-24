package com.vikmanz.shpppro.presentation.utils.extensions

import com.vikmanz.shpppro.data.model.ContactItem

fun MutableList<ContactItem>.findInList(
    contactItem: ContactItem
) = first { listItem -> listItem.contact == contactItem.contact }

fun <T> MutableList<T>.copyItem(contactItem: T, block: T.() -> T) {
    val oldItemIndex = indexOf(contactItem)
    if (oldItemIndex != -1) {
        val oldItem = this[oldItemIndex]
        set(oldItemIndex, oldItem.block())
    }
}

fun MutableList<ContactItem>.findInListAndCopy(
    contactItem: ContactItem,
    block: ContactItem.() -> ContactItem
) {
    val foundItem = find { listItem -> listItem.contact == contactItem.contact }
    if (foundItem != null) {
        copyItem(foundItem, block)
    }
}