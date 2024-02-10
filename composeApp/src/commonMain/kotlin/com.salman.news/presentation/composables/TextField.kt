package com.salman.news.presentation.composables

import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

/**
 * Created by Muhammed Salman email(mahmadslman@gmail.com) on 2/10/2024.
 */
val NTextFieldDefaults
    @Composable
    get() = TextFieldDefaults.colors(
        focusedContainerColor = MaterialTheme.colorScheme.background,
        errorContainerColor = MaterialTheme.colorScheme.error,
        errorTextColor = MaterialTheme.colorScheme.onErrorContainer,
        unfocusedContainerColor = MaterialTheme.colorScheme.background,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent
    )

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NBasicTextField(
    modifier: Modifier = Modifier,
    text: String,
    enabled: Boolean = true,
    singleLine: Boolean = true,
    label: String? = null,
    onTextChanged: (String) -> Unit,
) {
    BasicTextField(
        value = text,
        onValueChange = onTextChanged,
        modifier = modifier.border(2.dp, MaterialTheme.colorScheme.surface, shape = MaterialTheme.shapes.medium),
        enabled = enabled,
        singleLine = singleLine,
    ) { innerTextField ->
        TextFieldDefaults.DecorationBox(
            value = text,
            innerTextField = innerTextField,
            shape = MaterialTheme.shapes.medium,
            enabled = enabled,
            singleLine = singleLine,
            label = label?.let {
                {
                    Text(
                        it,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.5f),
                        modifier = Modifier.padding(vertical = 4.dp)
                    )
                }
            },
            visualTransformation = VisualTransformation.None,
            interactionSource = MutableInteractionSource(),
            colors = NTextFieldDefaults,
            contentPadding = PaddingValues(vertical = 4.dp, horizontal = 8.dp),
        )
    }
}