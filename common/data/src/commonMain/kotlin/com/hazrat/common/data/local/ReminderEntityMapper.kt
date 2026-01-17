package com.hazrat.common.data.local

import com.hazrat.common.domain.model.Reminder
import com.hazrat.common.domain.model.ReminderState
import com.hazrat.common.domain.model.RepeatOption
import com.hazrat.database.ReminderEntity

object ReminderEntityMapper {

    fun toDomain(entity: ReminderEntity): Reminder {
        return Reminder(
            id = entity.id,
            contactName = entity.contact_name,
            contactNumber = entity.contact_number,
            message = entity.message,

            scheduledAt = entity.scheduled_at,
            triggeredAt = entity.triggered_at,
            sentAt = entity.sent_at,

            repeatRule = entity.repeat_rule?.let { RepeatOption.valueOf(it) }?: RepeatOption.NONE,
            state = ReminderState.valueOf(entity.state),

            retryCount = entity.retry_count.toInt(),

            createdAt = entity.created_at,
            updatedAt = entity.updated_at
        )
    }

    fun toEntity(reminder: Reminder) : ReminderEntity {
        return ReminderEntity(
            id = reminder.id,
            contact_name = reminder.contactName,
            contact_number = reminder.contactNumber,
            message = reminder.message,

            scheduled_at = reminder.scheduledAt,
            triggered_at = reminder.triggeredAt,
            sent_at = reminder.sentAt,

            repeat_rule = reminder.repeatRule?.name,
            state = reminder.state.name,

            retry_count = reminder.retryCount.toLong(),

            created_at = reminder.createdAt,
            updated_at = reminder.updatedAt

        )
    }
}