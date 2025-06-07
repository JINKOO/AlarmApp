package com.jinkweonko.alarm.detail

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jinkweonko.alarm.R
import com.jinkweonko.core.ui.button.BottomFullButton
import com.jinkweonko.core.ui.theme.AlarmAppTheme
import com.jinkweonko.core.ui.topappbar.CommonTopAppBar
import java.time.LocalDateTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreen(
    modifier: Modifier = Modifier,
    viewModel: DetailViewModel,
    navigateUp: () -> Unit
) {
    var reminderTitle by remember { mutableStateOf("") }
    val scrollState = rememberScrollState()
    val context = LocalContext.current
    var selectedRingtoneUri by remember {
        mutableStateOf<Uri?>(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
    }
    var selectedRingtoneTitle by remember {
        mutableStateOf(getRingtoneTitle(context, selectedRingtoneUri))
    }
    val timePickerState = rememberTimePickerState(
        initialHour = LocalDateTime.now().hour,
        initialMinute = LocalDateTime.now().minute
    )

    val launchRingtonePicker = rememberRingtonePickerLauncher(
        currentRingtoneUri = selectedRingtoneUri,
        pickerActivityTitle = stringResource(id = R.string.select_alarm_sound),
        onRingtoneSelected = { uri, title ->
            selectedRingtoneUri = uri
            selectedRingtoneTitle = title
        }
    )

    LaunchedEffect(selectedRingtoneUri) {
        selectedRingtoneTitle = getRingtoneTitle(context, selectedRingtoneUri)
    }

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
                    viewModel.addReminder(
                        title = reminderTitle,
                        hour = timePickerState.hour,
                        minute = timePickerState.minute,
                        ringtoneTitle = selectedRingtoneTitle
                    )
                    navigateUp()
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
            TitleTextField(
                modifier = Modifier.fillMaxWidth(),
                reminderTitle = reminderTitle,
                onValueChange = { reminderTitle = it }
            )
            TimePickerBox(
                timePickerState = timePickerState
            )
            RingtoneSelectRow(
                modifier = Modifier.fillMaxWidth(),
                selectedRingtoneTitle = selectedRingtoneTitle,
                onClickRingtone = launchRingtonePicker
            )
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
    timePickerState: TimePickerState
) {
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
    modifier: Modifier = Modifier,
    selectedRingtoneTitle: String,
    onClickRingtone: () -> Unit
) {
    Column(
        modifier = modifier.clickable { onClickRingtone() },
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(R.string.title_select_ringtone),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
        Text(
            text = selectedRingtoneTitle,
            style = MaterialTheme.typography.bodyMedium,
            color = Color.LightGray
        )
    }
}

@Composable
internal fun rememberRingtonePickerLauncher(
    currentRingtoneUri: Uri?,
    pickerActivityTitle: String,
    onRingtoneSelected: (uri: Uri?, title: String) -> Unit
): () -> Unit {
    val context = LocalContext.current
    val ringtonePickerLauncherActivity = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val pickedUri: Uri? = result.data?.getParcelableExtra(RingtoneManager.EXTRA_RINGTONE_PICKED_URI)
            val finalUri = if (RingtoneManager.isDefault(pickedUri)) {
                RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
            } else {
                pickedUri
            }
            val title = getRingtoneTitle(context, finalUri)
            onRingtoneSelected(finalUri, title)
        }
    }

    return {
        val intent = Intent(RingtoneManager.ACTION_RINGTONE_PICKER).apply {
            putExtra(RingtoneManager.EXTRA_RINGTONE_TYPE, RingtoneManager.TYPE_ALARM)
            putExtra(RingtoneManager.EXTRA_RINGTONE_TITLE, pickerActivityTitle)
            putExtra(RingtoneManager.EXTRA_RINGTONE_EXISTING_URI, currentRingtoneUri)
            putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_DEFAULT, true)
            putExtra(RingtoneManager.EXTRA_RINGTONE_SHOW_SILENT, true)
        }
        ringtonePickerLauncherActivity.launch(intent)
    }
}

private fun getRingtoneTitle(context: Context, uri: Uri?): String {
    return when (uri) {
        null -> context.getString(R.string.silent_ringtone)
        else -> RingtoneManager.getRingtone(context, uri)?.getTitle(context) ?: context.getString(R.string.unknown_ringtone)
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