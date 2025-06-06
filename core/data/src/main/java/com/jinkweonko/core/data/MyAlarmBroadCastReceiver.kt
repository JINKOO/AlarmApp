package com.jinkweonko.core.data

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MyAlarmBroadCastReceiver : BroadcastReceiver() {

    companion object {
        const val ACTION_DISMISS = "ACTION_DISMISS"
        const val ACTION_SNOOZE = "ACTION_SNOOZE"
        const val SNOOZE_DURATION_MILLIS = 10 * 60 * 1000L
        const val WAKE_LOCK_DURATION_MILLIS = 60 * 1000L
    }

    override fun onReceive(context: Context, intent: Intent) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val reminderId = intent.getIntExtra("REMINDER_ID", -1)
        val reminderTitle = intent.getStringExtra("REMINDER_TITLE") ?: "알람 시간입니다!"
        Timber.d("onReceive() for alarmId: $reminderId / $reminderTitle. Creating Full-Screen Notification.")

        val powerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        val wakeLock = powerManager.newWakeLock(
            PowerManager.PARTIAL_WAKE_LOCK,
            "AlarmApp:WakeLock"
        )
        wakeLock.acquire(WAKE_LOCK_DURATION_MILLIS)
        try {
            when (intent.action) {
                ACTION_DISMISS -> {
                    Timber.d("ACTION_DISMISS for alarmId: $reminderId / $reminderTitle")
                    notificationManager.cancel(reminderId)
                }

                ACTION_SNOOZE -> {
                    Timber.d("ACTION_SNOOZE for alarmId: $reminderId / $reminderTitle")
                    snoozeAlarm(
                        context = context,
                        reminderId = reminderId,
                        reminderTitle = reminderTitle
                    )
                }

                else -> {
                    if (reminderId != -1) {
                        showNotificationWithActions(
                            context = context,
                            reminderId = reminderId,
                            reminderTitle = reminderTitle
                        )
                    }
                }
            }
        } finally {
            wakeLock.release()
        }
    }

    private fun showNotificationWithActions(
        context: Context,
        reminderId: Int,
        reminderTitle: String
    ) {
        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val channelId = "alarm_channel"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "나만의 알람",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "알람을 위한 채널입니다."
            }
            notificationManager.createNotificationChannel(channel)
        }

        val fullScreenIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("reminders://alarm/$reminderId")
        ).apply {
            putExtra("REMINDER_TITLE", reminderTitle)
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
        }
        val fullScreenPendingIntent = PendingIntent.getActivity(
            context,
            reminderId,
            fullScreenIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val dismissIntent = Intent(context, MyAlarmBroadCastReceiver::class.java).apply {
            action = ACTION_DISMISS
            putExtra("REMINDER_ID", reminderId)
        }
        val dismissPendingIntent = PendingIntent.getBroadcast(
            context,
            reminderId,
            dismissIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val snoozeIntent = Intent(context, MyAlarmBroadCastReceiver::class.java).apply {
            action = ACTION_SNOOZE
            putExtra("REMINDER_ID", reminderId)
            putExtra("REMINDER_TITLE", reminderTitle)
        }
        val snoozePendingIntent = PendingIntent.getBroadcast(
            context,
            reminderId + 1,
            snoozeIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notificationBuilder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("알람")
            .setContentText(reminderTitle)
            .setPriority(NotificationManagerCompat.IMPORTANCE_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setAutoCancel(true)
            .setFullScreenIntent(fullScreenPendingIntent, true)
            .addAction(0, "해제", dismissPendingIntent)
            .addAction(0, "10분 후 다시 울리기", snoozePendingIntent)
            .build()

        notificationManager.notify(reminderId, notificationBuilder)
    }

    private fun snoozeAlarm(
        context: Context,
        reminderId: Int,
        reminderTitle: String
    ) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, this@MyAlarmBroadCastReceiver::class.java).apply {
            putExtra("REMINDER_ID", reminderId)
            putExtra("REMINDER_TITLE", reminderTitle)
        }
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            reminderId,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val snoozeTime = System.currentTimeMillis() + SNOOZE_DURATION_MILLIS
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            snoozeTime,
            pendingIntent
        )
    }
}