package com.jinkweonko.alarm.navigation

interface AlarmAppDestination {
    val route: String
}

object Home : AlarmAppDestination {
    override val route = "home"
}

object AlarmDetail : AlarmAppDestination {
    override val route = "alarm_detail/{alarmId}"
}

object Alarm: AlarmAppDestination {
    override val route = "alarm/{alarmId}"
}
