package com.jinkweonko.alarm

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.jinkweonko.core.model.reminder.Reminder
import com.jinkweonko.util.extension.toMillis
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MyAlarmManager @Inject constructor(
    @ApplicationContext val context: Context
) {
    val alarmManager: AlarmManager =
        context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

    fun setReminder(reminder: Reminder) {
        val alarmIntent = Intent(context, MyAlarmBroadCastReceiver::class.java).apply {
            putExtra(MyAlarmBroadCastReceiver.REMINDER_ID, reminder.id)
            putExtra(MyAlarmBroadCastReceiver.REMINDER_TITLE, reminder.title)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminder.id,
            alarmIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            reminder.time.toMillis(),
            pendingIntent
        )
    }

    fun cancelReminder(reminderId: Int) {
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminderId,
            Intent(context, MyAlarmBroadCastReceiver::class.java),
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        alarmManager.cancel(pendingIntent)

        val serviceIntent = Intent(context, AlarmMusicService::class.java)
        context.stopService(serviceIntent)
    }
}