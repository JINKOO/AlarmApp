package com.jinkweonko.core.ui.button

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.jinkweonko.core.ui.theme.AlarmAppTheme

private val BOTTOM_BAR_HEIGHT = 56.dp

@Composable
fun BottomFullButton(
    modifier: Modifier = Modifier,
    title: String,
    enabled: Boolean = true,
    onClick: () -> Unit,
) {
    Button(
        modifier = modifier
            .fillMaxWidth()
            .height(BOTTOM_BAR_HEIGHT)
            .padding(horizontal = 8.dp),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color.Red,
            disabledContainerColor = Color.LightGray
        ),
        shape = RoundedCornerShape(16.dp),
        onClick = onClick,
        enabled = enabled
    ) {
        Text(
            text = title,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White
        )
    }
}

@Preview
@Composable
fun PreviewBottomFullButton() {
    AlarmAppTheme {
        BottomFullButton(
            title = "Save",
            enabled = true,
            onClick = {}
        )
    }
}

@Preview
@Composable
fun PreviewBottomFullButtonDisable() {
    AlarmAppTheme {
        BottomFullButton(
            title = "Save",
            enabled = false,
            onClick = {}
        )
    }
}