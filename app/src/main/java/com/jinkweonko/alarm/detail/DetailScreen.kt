package com.jinkweonko.alarm.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jinkweonko.alarm.R
import com.jinkweonko.core.ui.button.BottomFullButton
import com.jinkweonko.core.ui.theme.AlarmAppTheme
import com.jinkweonko.core.ui.topappbar.CommonTopAppBar
import timber.log.Timber
import java.time.LocalDateTime

@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel,
    navigateUp: () -> Unit
) {
    var reminderTitle by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    Scaffold(
        modifier = modifier,
        topBar = {
            CommonTopAppBar(title = stringResource(R.string.title_reminder_detail))
        },
        bottomBar = {
            BottomFullButton(
                title = stringResource(R.string.button_save),
                enabled = reminderTitle.isNotEmpty(),
                onClick = {
                    viewModel.addReminder()
//                    navigateUp()
                }
            )
        },
        containerColor = Color.Black
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(paddingValues = innerPadding)
                .padding(horizontal = 16.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            // 1. 리마인더 이름
            TitleTextField(
                modifier = Modifier.fillMaxWidth(),
                reminderTitle = reminderTitle,
                onValueChange = { reminderTitle = it }
            )
            // 2. 시간 설정
            TimePickerBox()

            // 3. 벨소리 설정
            RingtoneSelectRow()
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
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = reminderTitle,
            onValueChange = onValueChange,
            singleLine = true,
            colors = TextFieldDefaults.colors().copy(
                focusedContainerColor = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White
            ),
            placeholder = {
                Text(
                    text = stringResource(R.string.placeholder_reminder_title),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color.LightGray
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TimePickerBox(
    modifier: Modifier = Modifier,
    currentTime: LocalDateTime = LocalDateTime.now()
) {
    val timePickerState = rememberTimePickerState(
        initialHour = currentTime.hour,
        initialMinute = currentTime.minute
    )

    Timber.d("timePickerState : ${timePickerState.hour} : ${timePickerState.minute}")

    Column(
        modifier = modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            text = stringResource(R.string.title_time_picker),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            textAlign = TextAlign.Start
        )
        TimePicker(
            state = timePickerState
        )
    }
}

@Composable
private fun RingtoneSelectRow(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.title_select_ringtone),
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