package com.jinkweonko.alarm.reminder

import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.jinkweonko.core.ui.theme.AlarmAppTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class AlarmActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val alarmId = intent.data?.lastPathSegment
        val message = intent.getStringExtra("REMINDER_MESSAGE") ?: "알람 시간입니다!"

        Timber.d("AlarmActivity onCreate() for alarmId: $alarmId with message: $message")
        turnOnScreen()
        setContent {
            AlarmAppTheme {
                AlarmScreen(
                    alarmMessage = message,
                    onDismiss = {
                        finish()
                    }
                )
            }
        }
    }

    private fun turnOnScreen() {
        // 잠금화면 위로 Activity를 보여주고, 화면을 켭니다.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
        } else {
            @Suppress("DEPRECATION")
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                        WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                        WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON or
                        WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON
            )
        }
    }
}

@Composable
fun AlarmScreen(alarmMessage: String, onDismiss: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "⏰", fontSize = 80.sp)
        Spacer(modifier = Modifier.height(24.dp))
        Text(text = alarmMessage, fontSize = 24.sp, textAlign = TextAlign.Center)
        Spacer(modifier = Modifier.height(48.dp))
        Button(onClick = onDismiss) {
            Text("알람 끄기", fontSize = 18.sp)
        }
    }
}
