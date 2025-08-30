package com.jinkweonko.alarm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.Ringtone
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlarmMusicService : Service() {

    private lateinit var ringtone: Ringtone
    private val CHANNEL_ID = "ALARM_SERVICE_CHANNEL"

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_DISMISS = "ACTION_DISMISS"
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        val reminderId = intent?.getIntExtra(MyAlarmBroadCastReceiver.REMINDER_ID, 0) ?: 0
        val reminderTitle = intent?.getStringExtra(MyAlarmBroadCastReceiver.REMINDER_TITLE) ?: "리마인더"
        when(intent?.action) {
            ACTION_START -> {
                val notification = createNotification(reminderId, reminderTitle)
                startForeground(1, notification)
                playRingtone()
            }
            ACTION_DISMISS -> {
                stopSelf()
            }
        }
        return START_NOT_STICKY
    }

    private fun playRingtone() {
        val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
        audioManager.setStreamVolume(
            AudioManager.STREAM_ALARM,
            audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM),
            AudioManager.FLAG_PLAY_SOUND
        )

        val ringtoneUrl = RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_ALARM)
        ringtone = RingtoneManager.getRingtone(this, ringtoneUrl)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ringtone.isLooping = true
            ringtone.audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ALARM)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
        }
        ringtone.play()
    }

    private fun createNotification(reminderId: Int, reminderTitle: String): Notification {
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "알람 벨소리 채널",
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(serviceChannel)
        }
        val fullScreenIntent = Intent(
            Intent.ACTION_VIEW,
            Uri.parse("reminders://alarm/$reminderId")
        )
        val fullScreenPendingIntent = PendingIntent.getActivity(
            this,
            0,
            fullScreenIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )
        val dismissIntent = Intent(this, AlarmMusicService::class.java).apply {
            action = ACTION_DISMISS
        }
        val dismissPendingIntent = PendingIntent.getService(
            this,
            reminderId,
            dismissIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setContentTitle("리마인더")
            .setContentText(reminderTitle)
            .setPriority(NotificationManagerCompat.IMPORTANCE_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setFullScreenIntent(fullScreenPendingIntent, true)
            .setAutoCancel(true)
            .addAction(0, "해제", dismissPendingIntent)
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        ringtone.stop()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}