package com.hazrat.contacts


/**
 * @author hazratummar
 * Created on 11/01/26
 */


data class Contact(
    val id: String,
    val name: String,
    val phoneNumbers: List<String>
){
    fun search(query: String): Boolean {
        val matchingCombinations = listOf(
            name,
            *phoneNumbers.toTypedArray()
        )
        return matchingCombinations.any {
            it.contains(query, ignoreCase = true)
        }
    }
}

expect class ContactManager {

    suspend fun getContacts () : List<Contact>

}