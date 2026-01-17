package com.hazrat.contacts

import kotlinx.cinterop.ExperimentalForeignApi
import platform.Contacts.CNContactFamilyNameKey
import platform.Contacts.CNContactFetchRequest
import platform.Contacts.CNContactGivenNameKey
import platform.Contacts.CNContactPhoneNumbersKey
import platform.Contacts.CNContactStore
import platform.Contacts.CNLabeledValue
import platform.Contacts.CNPhoneNumber
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

actual class ContactManager {
    @OptIn(ExperimentalForeignApi::class)
    actual suspend fun getContacts(): List<Contact> = suspendCoroutine { continuation ->

        val store = CNContactStore()
        val keys = listOf(
            CNContactGivenNameKey,
            CNContactFamilyNameKey,
            CNContactPhoneNumbersKey
        )

        val request = CNContactFetchRequest(keysToFetch = keys)
        val contacts = mutableListOf<Contact>()

        try {
            store.enumerateContactsWithFetchRequest(request, null){contact, _ ->
                contact?.let { cnContact ->
                    val fullName = "${cnContact.givenName} ${cnContact.familyName}"

                    val phoneNumbers = cnContact.phoneNumbers.mapNotNull { labeledValue ->
                        val phoneNumber = (labeledValue as? CNLabeledValue)?.value as? CNPhoneNumber
                        phoneNumber?.stringValue ?: ""
                    }.filter { it.isNotEmpty() }

                    println("iOS Contact: $fullName has ${phoneNumbers.size} phone numbers")

                    if (phoneNumbers.isNotEmpty()){
                        contacts.add(
                            Contact(
                                id = cnContact.identifier,
                                name = fullName.ifEmpty { "Unknown" },
                                phoneNumbers = phoneNumbers
                            )
                        )
                    }
                }
            }
            continuation.resume(contacts)
        }catch (e: Exception) {
            continuation.resumeWithException(e)
        }
    }
}