package com.hazrat.common.data.repository

import com.hazrat.common.data.local.ReminderEntityMapper
import com.hazrat.common.domain.model.Reminder
import com.hazrat.common.domain.model.ReminderState
import com.hazrat.common.domain.repository.ReminderRepository
import com.hazrat.messagelater.database.AppDatabase
import com.hazrat.notification.LocalNotificationManager
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlin.time.Clock


/**
 * @author hazratummar
 * Created on 13/01/26
 */

class ReminderRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val notificationManager: LocalNotificationManager
) : ReminderRepository {
    private val queries = appDatabase.appDatabaseQueries

    override suspend fun getAll(): List<Reminder> = withContext(Dispatchers.Default) {
        queries.selectAllReminders()
            .executeAsList()
            .map(ReminderEntityMapper::toDomain)
    }

    override suspend fun getActiveReminder(): List<Reminder> = withContext(Dispatchers.Default){
        queries.selectActiveReminders()
            .executeAsList()
            .map(ReminderEntityMapper::toDomain)
    }

    override suspend fun getReminderById(id: String): Reminder? = withContext(Dispatchers.Default){
        queries.selectReminderById(id = id)
            .executeAsOneOrNull()
            ?.let(ReminderEntityMapper::toDomain)
    }

    override suspend fun insert(reminder: Reminder) = withContext(Dispatchers.Default) {

        val notificationScheduled = notificationManager.scheduleNotification(
            id = reminder.id,
            title = reminder.contactName,
            body = reminder.message,
            timestamp = reminder.scheduledAt,
            whatsappNumber = reminder.contactNumber,
            whatsappMessage = reminder.message
        )
        if (notificationScheduled){
            val e = ReminderEntityMapper.toEntity(reminder = reminder)
            queries.insertReminder(
                id = e.id,
                contact_name = e.contact_name,
                contact_number = e.contact_number,
                message = e.message,
                scheduled_at = e.scheduled_at,
                triggered_at = e.triggered_at,
                sent_at = e.sent_at,
                repeat_rule = e.repeat_rule,
                state = e.state,
                retry_count = e.retry_count,
                created_at = e.created_at,
                updated_at = e.updated_at
            )
        }

    }

    override suspend fun updateReminder(reminder: Reminder) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteReminder(id: String) {
        queries.deleteReminder(id = id)
    }

    override suspend fun updateReminderState(
        id: String,
        state: ReminderState,
        triggeredAt: Long?,
        sentAt: Long?
    ) {
        val now = Clock.System.now().epochSeconds
        queries.updateReminderState(
            state = state.name,
            triggered_at = triggeredAt ?: now,
            sent_at = sentAt,
            updated_at = now,
            id = id
        )
    }
}