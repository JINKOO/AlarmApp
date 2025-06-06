package com.jinkweonko.alarm

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.jinkweonko.alarm.navigation.AlarmAppNavHost
import com.jinkweonko.core.ui.theme.AlarmAppTheme
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlarmAppTheme {
                AlarmAppNavHost()
            }
        }
    }
}