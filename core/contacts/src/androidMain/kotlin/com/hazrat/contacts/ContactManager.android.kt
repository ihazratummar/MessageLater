package com.hazrat.contacts

import android.content.Context
import android.os.Build
import android.provider.ContactsContract
import androidx.annotation.RequiresApi

actual class ContactManager(
    private val context: Context
) {

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    actual suspend fun getContacts(): List<Contact> {
        val contacts = mutableListOf<Contact>()

        val cursor = context.contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            arrayOf(
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Phone.NUMBER,
            ),
            null,
            null,
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC"
        )

        cursor?.use {
            val idIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID)
            val nameIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
            val numberIndex = it.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)

            val contactMap = mutableMapOf<String, MutableList<String>>()
            val contactNames = mutableMapOf<String, String>()

            while (it.moveToNext()){
                val id = it.getString(idIndex)
                val name = it.getString(nameIndex) ?: "Unknown"
                val number = it.getString(numberIndex) ?: ""

                contactNames[id] = name
                contactMap.getOrPut(id) {mutableListOf()}.add(number)
            }

            contactMap.forEach { (id, numbers) ->
                contacts.add(
                    Contact(
                        id = id,
                        name = contactNames[id] ?: "Unknown",
                        phoneNumbers = numbers
                    )
                )
            }
        }
        return contacts

    }
}