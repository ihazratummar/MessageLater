package com.hazrat.common.domain.repository

import com.hazrat.common.domain.model.Reminder
import com.hazrat.common.domain.model.ReminderState


/**
 * @author hazratummar
 * Created on 13/01/26
 */

interface ReminderRepository {

    suspend fun getAll() : List<Reminder>

    suspend fun getActiveReminder() : List<Reminder>

    suspend fun getReminderById(id: String) : Reminder?

    suspend fun insert(reminder: Reminder)

    suspend fun updateReminder(reminder: Reminder)

    suspend fun deleteReminder(id: String)

    suspend fun updateReminderState(id: String, state: ReminderState, triggeredAt: Long? = null, sentAt: Long? = null)


}