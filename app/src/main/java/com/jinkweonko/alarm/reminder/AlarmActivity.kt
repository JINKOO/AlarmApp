package com.jinkweonko.alarm.reminder

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import com.jinkweonko.alarm.MainActivity
import com.jinkweonko.core.ui.theme.AlarmAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AlarmActivity : ComponentActivity() {

    private val viewModel by viewModels<ReminderViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val reminderId = intent.data?.lastPathSegment.orEmpty()
        if (reminderId.isEmpty()) {
            navigateToMain()
            return
        }
        val message = intent.getStringExtra("REMINDER_MESSAGE") ?: "알람 시간입니다!"
        turnOnScreen()
        setContent {
            AlarmAppTheme {
                AlarmScreen(
                    alarmMessage = message,
                    onDismiss = {
                        viewModel.dismissAlarm(reminderId.toInt())
                        navigateToMain()
                    }
                )
            }
        }
    }

    private fun turnOnScreen() {
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

    private fun navigateToMain() {
        val intent = Intent(this@AlarmActivity, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP or Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }
        startActivity(intent)
        finish()
    }
}

@Composable
fun AlarmScreen(
    alarmMessage: String,
    onDismiss: () -> Unit
) {
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
