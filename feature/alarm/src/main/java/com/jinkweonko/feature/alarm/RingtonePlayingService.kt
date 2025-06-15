package com.jinkweonko.feature.alarm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import com.jinkweonko.core.data.R

class RingtonePlayingService : Service() {

    private lateinit var ringtone: Ringtone
    private lateinit var audioManager: AudioManager
    private var originalRingerMode: Int = AudioManager.RINGER_MODE_NORMAL
    private val NOTIFICATION_ID = 1
    private val CHANNEL_ID = "ringtone_service_channel"

    companion object {
        const val ACTION_START = "ACTION_START"
        const val ACTION_DISMISS = "ACTION_DISMISS"
        const val EXTRA_REMINDER_TITLE = "EXTRA_REMINDER_TITLE"
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_START -> {
                val reminderTitle = intent.getStringExtra(EXTRA_REMINDER_TITLE) ?: "알람"
                startForeground(NOTIFICATION_ID, createNotification(reminderTitle))
                playRingtone()
            }
            ACTION_DISMISS -> {
                stopSelf() // This will trigger onDestroy
            }
        }
        return START_NOT_STICKY
    }

    private fun playRingtone() {
        // Get AudioManager instance
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
        
        // Save current ringer mode
        originalRingerMode = audioManager.ringerMode
        
        // Set ringer mode to normal to ensure sound plays
        audioManager.ringerMode = AudioManager.RINGER_MODE_NORMAL
        
        // Set volume to maximum
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_ALARM)
        audioManager.setStreamVolume(
            AudioManager.STREAM_ALARM,
            maxVolume,
            AudioManager.FLAG_PLAY_SOUND
        )

        // Get the default alarm ringtone
        val ringtoneUri = RingtoneManager.getActualDefaultRingtoneUri(this, RingtoneManager.TYPE_ALARM)
        ringtone = RingtoneManager.getRingtone(this, ringtoneUri)
        
        // Set audio attributes for alarm stream
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            ringtone.isLooping = true
            ringtone.audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ALARM)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()
        }
        
        ringtone.play()
    }

    private fun createNotification(reminderTitle: String): Notification {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(
                CHANNEL_ID,
                "알람 벨소리 채널",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                // Set sound and vibration for the notification channel
                setSound(null, null) // Disable notification sound as we're playing ringtone
                enableVibration(true)
            }
            val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(serviceChannel)
        }

        // Dismiss action
        val dismissIntent = Intent(this, RingtonePlayingService::class.java).apply {
            action = ACTION_DISMISS
        }
        val dismissPendingIntent = PendingIntent.getService(
            this, 0, dismissIntent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("알람")
            .setContentText(reminderTitle)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .addAction(0, "해제", dismissPendingIntent)
            .build()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (::ringtone.isInitialized) {
            ringtone.stop()
        }
        // Restore original ringer mode
        if (::audioManager.isInitialized) {
            audioManager.ringerMode = originalRingerMode
        }
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
} 