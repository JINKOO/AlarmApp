package com.jinkweonko.alarm.reminder

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jinkweonko.alarm.R
import com.jinkweonko.core.ui.theme.AlarmAppTheme
import com.jinkweonko.core.ui.topappbar.CommonTopAppBar

@Composable
fun ReminderScreen(
    modifier: Modifier = Modifier,
) {
    Scaffold(
        modifier = modifier,
        topBar = {
            CommonTopAppBar(title = stringResource(R.string.title_reminder_detail))
        },
        containerColor = Color.White
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(paddingValues = innerPadding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
        }
    }
}

@Composable
private fun TitleTextField(
    modifier: Modifier = Modifier,
    reminderTitle: String,
    onValueChange: (String) -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.title_reminder),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Preview
@Composable
fun PreviewTitleTextField() {
    AlarmAppTheme {
        TitleTextField(
            reminderTitle = "리마인더 이름"
        )
    }
}