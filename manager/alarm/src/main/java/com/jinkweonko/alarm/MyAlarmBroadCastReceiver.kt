package com.jinkweonko.alarm

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MyAlarmBroadCastReceiver : BroadcastReceiver() {

    companion object {
        const val REMINDER_ID = "REMINDER_ID"
        const val REMINDER_TITLE = "REMINDER_TITLE"
    }

    override fun onReceive(context: Context, intent: Intent) {
        val reminderId = intent.getIntExtra(REMINDER_ID, 0)
        val reminderTitle = intent.getStringExtra(REMINDER_TITLE) ?: "알람 시간입니다!"

        val serviceIntent = Intent(context, AlarmMusicService::class.java).apply {
            action = AlarmMusicService.ACTION_START
            putExtra(REMINDER_ID, reminderId)
            putExtra(REMINDER_TITLE, reminderTitle)
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(serviceIntent)
        } else {
            context.startService(serviceIntent)
        }
    }
}