package com.jinkweonko.alarm.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jinkweonko.alarm.R
import com.jinkweonko.core.model.reminder.Reminder
import com.jinkweonko.core.ui.button.AddReminderButton
import com.jinkweonko.core.ui.theme.AlarmAppTheme
import com.jinkweonko.core.ui.theme.Typography
import com.jinkweonko.core.ui.topappbar.CommonTopAppBar
import com.jinkweonko.util.extension.toTimeFormat

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    navigateToDetail: (Int?) -> Unit
) {
    val reminders by viewModel.reminders.collectAsStateWithLifecycle()
    Scaffold(
        topBar = {
            CommonTopAppBar(
                title = stringResource(R.string.title_home_screen)
            )
        },
        containerColor = Color.Black
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(16.dp)
            )
            AddReminderButton(
                onClick = { navigateToDetail(null) }
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(32.dp)
            )
            ReminderLazyColumn(
                reminders = reminders,
                updateActiveState = { reminder, activeState ->
                    viewModel.updateActiveState(
                        reminder,
                        activeState
                    )
                },
                onClickReminder = navigateToDetail
            )
        }
    }
}

@Composable
private fun ReminderLazyColumn(
    reminders: List<Reminder>,
    onClickReminder: (Int) -> Unit,
    updateActiveState: (Reminder, Boolean) -> Unit
) {
    val lazyListState = rememberLazyListState()
    LazyColumn(
        state = lazyListState,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        itemsIndexed(reminders) { index, reminder ->
            ReminderItem(
                reminder = reminder,
                onClickReminder = onClickReminder,
                updateActiveState = { updateActiveState(reminder, it) }
            )
        }
    }
}

@Composable
private fun ReminderItem(
    modifier: Modifier = Modifier,
    reminder: Reminder,
    onClickReminder: (Int) -> Unit,
    updateActiveState: (Boolean) -> Unit
) {
    Row(
        modifier = modifier
            .background(color = Color.White, shape = RoundedCornerShape(16.dp))
            .padding(horizontal = 16.dp, vertical = 32.dp)
            .clickable { onClickReminder(reminder.id) },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = reminder.amPm,
                style = Typography.bodyLarge
            )
            Text(
                text = reminder.time.toTimeFormat(),
                style = Typography.headlineLarge
            )
        }
        Spacer(
            modifier = Modifier.weight(1f),
        )
        Text(
            text = reminder.title,
            maxLines = 1,
            style = Typography.bodyLarge,
            overflow = TextOverflow.Ellipsis,
        )
        Switch(
            checked = reminder.isActive,
            onCheckedChange = { updateActiveState(it) }
        )
    }
}

@Composable
@Preview
private fun PreviewReminderItem() {
    AlarmAppTheme {
        ReminderItem(
            reminder = Reminder(
                title = "테스트",
                isActive = true
            ),
            onClickReminder = {},
            updateActiveState = {}
        )
    }
}