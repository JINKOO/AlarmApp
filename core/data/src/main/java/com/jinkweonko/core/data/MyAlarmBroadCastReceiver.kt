package com.jinkweonko.core.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.widget.Toast
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MyAlarmBroadCastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        Timber.d("====onReceive()===")
        // TODO 알람 배경음악과 함께 화면 수행
        // TODO Service로 음악 재생하기
    }
}