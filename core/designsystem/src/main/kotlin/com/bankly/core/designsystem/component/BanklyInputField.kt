package com.bankly.core.designsystem.component

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BanklyInputField(
    textFieldValue: TextFieldValue,
    onTextFieldValueChange: (TextFieldValue) -> Unit,
    placeholderText: String,
    labelText: String,
    isEnabled: Boolean = true,
    readOnly: Boolean = false,
    isError: Boolean = false,
    feedbackText: String = "",
    isPasswordField: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
) {
    var isVisible by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }

    val textFieldColors = TextFieldDefaults.textFieldColors(
        textColor = MaterialTheme.colorScheme.tertiary,
        containerColor = if (!isEnabled) MaterialTheme.colorScheme.tertiaryContainer
        else if (isError) MaterialTheme.colorScheme.errorContainer
        else MaterialTheme.colorScheme.primaryContainer.copy(0.7f),
        cursorColor = if (isError) MaterialTheme.colorScheme.error
        else MaterialTheme.colorScheme.primary,
        errorCursorColor = MaterialTheme.colorScheme.error,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        errorIndicatorColor = Color.Transparent,
        focusedLeadingIconColor = if (!isEnabled) MaterialTheme.colorScheme.tertiary
        else if (isError) MaterialTheme.colorScheme.error
        else MaterialTheme.colorScheme.primary,
        unfocusedLeadingIconColor = if (!isEnabled) MaterialTheme.colorScheme.tertiary
        else if (isError) MaterialTheme.colorScheme.error
        else MaterialTheme.colorScheme.primary,
        errorLeadingIconColor = MaterialTheme.colorScheme.error,
        focusedTrailingIconColor = if (!isEnabled) MaterialTheme.colorScheme.tertiary
        else if (isError) MaterialTheme.colorScheme.error
        else MaterialTheme.colorScheme.primary,
        unfocusedTrailingIconColor = if (!isEnabled) MaterialTheme.colorScheme.tertiary
        else if (isError) MaterialTheme.colorScheme.error
        else MaterialTheme.colorScheme.primary,
        errorTrailingIconColor = MaterialTheme.colorScheme.error,
        focusedLabelColor = if (!isEnabled) MaterialTheme.colorScheme.tertiary
        else if (isError) MaterialTheme.colorScheme.error
        else MaterialTheme.colorScheme.primary,
        unfocusedLabelColor = if (!isEnabled) MaterialTheme.colorScheme.tertiary
        else if (isError) MaterialTheme.colorScheme.error
        else MaterialTheme.colorScheme.primary,
        errorLabelColor = MaterialTheme.colorScheme.error,
        errorSupportingTextColor = MaterialTheme.colorScheme.error,
        focusedPrefixColor = if (!isEnabled) MaterialTheme.colorScheme.tertiary
        else if (isError) MaterialTheme.colorScheme.error
        else MaterialTheme.colorScheme.primary,
        unfocusedPrefixColor = if (!isEnabled) MaterialTheme.colorScheme.tertiary
        else if (isError) MaterialTheme.colorScheme.error
        else MaterialTheme.colorScheme.primary,
        errorPrefixColor = MaterialTheme.colorScheme.error,
        focusedSuffixColor = if (!isEnabled) MaterialTheme.colorScheme.tertiary
        else if (isError) MaterialTheme.colorScheme.error
        else MaterialTheme.colorScheme.primary,
        unfocusedSuffixColor = if (!isEnabled) MaterialTheme.colorScheme.tertiary
        else if (isError) MaterialTheme.colorScheme.error
        else MaterialTheme.colorScheme.primary,
        errorSuffixColor = MaterialTheme.colorScheme.error,
        focusedSupportingTextColor = if (!isEnabled) MaterialTheme.colorScheme.tertiary
        else if (isError) MaterialTheme.colorScheme.error
        else MaterialTheme.colorScheme.primary,
        unfocusedSupportingTextColor = if (!isEnabled) MaterialTheme.colorScheme.tertiary
        else if (isError) MaterialTheme.colorScheme.error
        else MaterialTheme.colorScheme.primary,
        disabledTrailingIconColor = Color.Unspecified,
        disabledLabelColor = MaterialTheme.colorScheme.tertiary,
        disabledLeadingIconColor = MaterialTheme.colorScheme.tertiary,
        disabledPlaceholderColor = MaterialTheme.colorScheme.tertiary,
        disabledPrefixColor = MaterialTheme.colorScheme.tertiary,
        disabledSuffixColor = MaterialTheme.colorScheme.tertiary,
        disabledSupportingTextColor = MaterialTheme.colorScheme.tertiary,
        disabledTextColor = MaterialTheme.colorScheme.tertiary,
        placeholderColor = MaterialTheme.colorScheme.onPrimaryContainer,
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        Text(
            text = labelText,
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(horizontal = 24.dp)
        )
        if (isPasswordField) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp)
                    .border(
                        width = 1.dp,
                        color = if (isFocused)
                            if (!isEnabled) MaterialTheme.colorScheme.tertiary
                            else if (isError) MaterialTheme.colorScheme.error
                            else MaterialTheme.colorScheme.primary
                        else Color.Transparent,
                        shape = MaterialTheme.shapes.medium
                    )
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                    },
                value = textFieldValue,
                onValueChange = onTextFieldValueChange,
                enabled = isEnabled,
                readOnly = readOnly,
                isError = isError,
                singleLine = true,
                placeholder = {
                    Text(
                        text = placeholderText,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onPrimaryContainer
                        )
                    )
                },
                keyboardOptions = keyboardOptions,
                textStyle = MaterialTheme.typography.bodyMedium,
                shape = MaterialTheme.shapes.medium,
                visualTransformation = if (isVisible) VisualTransformation.None
                else PasswordVisualTransformation('*'),
                trailingIcon = {
                    Icon(
                        painter = painterResource(
                            id = if (isVisible) BanklyIcons.VisibilityOff
                            else BanklyIcons.VisibilityOn
                        ),
                        contentDescription = "Visibility Icon",
                        modifier = Modifier
                            .size(24.dp)
                            .clip(MaterialTheme.shapes.small)
                            .clickable(
                                onClick = {
                                    isVisible = !isVisible
                                },
                                enabled = true,
                                role = Role.Button,
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(
                                    bounded = true,
                                    color = if (isError) MaterialTheme.colorScheme.error
                                    else MaterialTheme.colorScheme.primary
                                )
                            ),
                        tint = if (isError) MaterialTheme.colorScheme.error
                        else MaterialTheme.colorScheme.primary
                    )
                },
                colors = textFieldColors
            )
        } else {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp, vertical = 8.dp)
                    .border(
                        width = 1.dp,
                        color = if (isFocused)
                            if (!isEnabled) MaterialTheme.colorScheme.tertiary
                            else if (isError) MaterialTheme.colorScheme.error
                            else MaterialTheme.colorScheme.primary
                        else Color.Transparent,
                        shape = MaterialTheme.shapes.medium
                    )
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                    },
                value = textFieldValue,
                placeholder = {
                    Text(
                        text = placeholderText,
                        style = MaterialTheme.typography.bodyMedium
                    )
                },
                onValueChange = onTextFieldValueChange,
                enabled = isEnabled,
                readOnly = readOnly,
                isError = isError,
                singleLine = true,
                keyboardOptions = keyboardOptions,
                textStyle = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.tertiary
                ),
                shape = MaterialTheme.shapes.medium,
                colors = textFieldColors
            )
        }
        Text(
            text = feedbackText,
            style = MaterialTheme.typography.bodySmall.copy(
                color = if (!isEnabled) MaterialTheme.colorScheme.tertiary
                else if (isError) MaterialTheme.colorScheme.error
                else MaterialTheme.colorScheme.primary
            ),
            modifier = Modifier.padding(horizontal = 24.dp)
        )
    }
}


/**
 * Previews
 */
@Composable
@Preview(showBackground = true)
private fun BanklyInputFieldPreview1() {
    BanklyTheme {
        BanklyInputField(
            textFieldValue = TextFieldValue(text = ""),
            onTextFieldValueChange = {},
            labelText = "Phone Number",
            placeholderText = "e.g 08167039661",
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun BanklyInputFieldPreview2() {
    BanklyTheme {
        BanklyInputField(
            textFieldValue = TextFieldValue(text = ""),
            onTextFieldValueChange = {},
            labelText = "Passcode",
            placeholderText = "Input password",
            isPasswordField = true,
            isError = true,
            feedbackText = "Invalid input"
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun BanklyInputFieldPreview3() {
    BanklyTheme {
        BanklyInputField(
            textFieldValue = TextFieldValue(text = ""),
            onTextFieldValueChange = {},
            labelText = "Home address",
            placeholderText = "Enter your home address",
        )
    }
}

@Composable
@Preview(showBackground = true)
private fun BanklyInputFieldPreview4() {
    BanklyTheme {
        BanklyInputField(
            textFieldValue = TextFieldValue(text = "Hassan Abdulwahab"),
            onTextFieldValueChange = {},
            labelText = "Name",
            placeholderText = "What's your name?",
            isEnabled = false,
        )
    }
}