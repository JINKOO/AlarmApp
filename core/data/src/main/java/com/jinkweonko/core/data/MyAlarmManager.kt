package com.jinkweonko.core.data

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.jinkweonko.core.data.entity.ReminderEntity
import dagger.hilt.android.qualifiers.ApplicationContext
import java.time.LocalDateTime
import java.time.ZoneId
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyAlarmManager @Inject constructor(
    @ApplicationContext val context: Context
) {
    val alarmManager: AlarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun setReminder(reminderEntity: ReminderEntity) {
        val alarmIntent = Intent(context, MyAlarmBroadCastReceiver::class.java).apply {
            putExtra("REMINDER_ID", reminderEntity.id)
            putExtra("REMINDER_TITLE", reminderEntity.title)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminderEntity.id,
            alarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            reminderEntity.time.atZone(ZoneId.systemDefault()).toEpochSecond(),
            pendingIntent
        )
    }

    fun cancelReminder(reminderEntity: ReminderEntity) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                reminderEntity.id,
                Intent(context, MyAlarmBroadCastReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}