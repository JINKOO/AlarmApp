package com.jinkweonko.alarm.home

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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.jinkweonko.alarm.R
import com.jinkweonko.core.domain.model.Reminder
import com.jinkweonko.core.ui.button.AddReminderButton
import com.jinkweonko.core.ui.theme.Typography
import com.jinkweonko.core.ui.topappbar.CommonTopAppBar

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
    ) {
        items(reminders) { reminder ->
            ReminderItem(
                reminder = reminder,
                onClickReminder = onClickReminder,
                updateActiveState = { updateActiveState(reminder, it) }
            )
            HorizontalDivider(
                modifier = Modifier.fillMaxWidth(),
                color = Color.LightGray,
                thickness = 1.dp
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
        modifier = modifier.clickable { onClickReminder(reminder.id) },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = reminder.time.toString(),
                style = Typography.headlineLarge,
                color = Color.White
            )
            Text(
                text = reminder.title,
                style = Typography.bodySmall,
                color = Color.White
            )
        }
        Spacer(
            modifier = Modifier.weight(1f),
        )
        Switch(
            checked = reminder.isActive,
            onCheckedChange = { updateActiveState(it) }
        )
    }
}