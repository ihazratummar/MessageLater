package com.hazrat.common.domain.model

data class Reminder(
    val id: String,

    val contactName: String,
    val contactNumber: String,

    val message: String,

    val scheduledAt: Long,
    val triggeredAt: Long,
    val sentAt: Long? = null,

    val repeatRule: RepeatOption?,
    val state: ReminderState,

    val retryCount: Int,

    val createdAt: Long,
    val updatedAt: Long
)


enum class RepeatOption {
    NONE,
    MONTHLY,
    YEARLY
}


enum class ReminderState {
    DRAFT,
    SCHEDULED,
    PENDING,
    SENT,
    MISSED,
    CANCELLED
}