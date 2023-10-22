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
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.bankly.core.designsystem.icon.BanklyIcons
import com.bankly.core.designsystem.theme.BanklyTheme
import com.bankly.core.designsystem.theme.PreviewColor

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BanklyInputField(
    textFieldValue: TextFieldValue,
    onTextFieldValueChange: (TextFieldValue) -> Unit,
    placeholderText: String = "",
    labelText: String = "",
    isEnabled: Boolean = true,
    readOnly: Boolean = false,
    isError: Boolean = false,
    feedbackText: String = "",
    isPasswordField: Boolean = false,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    trailingIcon: Int? = null,
    onTrailingIconClick: () -> Unit = {},
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    horizontalPadding: Dp = 24.dp
) {
    var isVisible by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }

    val textFieldColors = TextFieldDefaults.colors(
        focusedTextColor = MaterialTheme.colorScheme.tertiary,
        unfocusedTextColor = MaterialTheme.colorScheme.tertiary,
        disabledTextColor = MaterialTheme.colorScheme.tertiary,
        errorTextColor = MaterialTheme.colorScheme.error,
        focusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
        unfocusedContainerColor = MaterialTheme.colorScheme.primaryContainer,
        disabledContainerColor = MaterialTheme.colorScheme.tertiaryContainer,
        errorContainerColor = MaterialTheme.colorScheme.errorContainer,
        cursorColor = MaterialTheme.colorScheme.primary,
        errorCursorColor = MaterialTheme.colorScheme.error,
        focusedIndicatorColor = Color.Transparent,
        unfocusedIndicatorColor = Color.Transparent,
        disabledIndicatorColor = Color.Transparent,
        errorIndicatorColor = Color.Transparent,
        focusedLeadingIconColor = MaterialTheme.colorScheme.primary,
        unfocusedLeadingIconColor = MaterialTheme.colorScheme.primary,
        disabledLeadingIconColor = MaterialTheme.colorScheme.tertiary,
        errorLeadingIconColor = MaterialTheme.colorScheme.error,
        focusedTrailingIconColor = MaterialTheme.colorScheme.primary,
        unfocusedTrailingIconColor = MaterialTheme.colorScheme.primary,
        disabledTrailingIconColor = MaterialTheme.colorScheme.tertiary,
        errorTrailingIconColor = MaterialTheme.colorScheme.error,
        focusedLabelColor = MaterialTheme.colorScheme.primary,
        unfocusedLabelColor = MaterialTheme.colorScheme.primary,
        disabledLabelColor = MaterialTheme.colorScheme.tertiary,
        errorLabelColor = MaterialTheme.colorScheme.error,
        focusedSupportingTextColor = MaterialTheme.colorScheme.primary,
        unfocusedSupportingTextColor = MaterialTheme.colorScheme.primary,
        disabledSupportingTextColor = MaterialTheme.colorScheme.tertiary,
        errorSupportingTextColor = MaterialTheme.colorScheme.error,
        focusedPrefixColor = MaterialTheme.colorScheme.primary,
        unfocusedPrefixColor = MaterialTheme.colorScheme.primary,
        errorPrefixColor = MaterialTheme.colorScheme.error,
        disabledPrefixColor = MaterialTheme.colorScheme.tertiary,
        focusedSuffixColor = MaterialTheme.colorScheme.primary,
        unfocusedSuffixColor = MaterialTheme.colorScheme.primary,
        disabledSuffixColor = MaterialTheme.colorScheme.tertiary,
        errorSuffixColor = MaterialTheme.colorScheme.error,
        focusedPlaceholderColor = MaterialTheme.colorScheme.onPrimaryContainer,
        unfocusedPlaceholderColor = MaterialTheme.colorScheme.onPrimaryContainer,
        disabledPlaceholderColor = MaterialTheme.colorScheme.tertiary,
        errorPlaceholderColor = MaterialTheme.colorScheme.error,
    )

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
    ) {
        if (labelText.isNotEmpty()) {
            Text(
                text = labelText,
                style = MaterialTheme.typography.titleSmall,
                modifier = Modifier.padding(horizontal = horizontalPadding),
            )
        }
        if (isPasswordField) {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = horizontalPadding, vertical = 8.dp)
                    .border(
                        width = 1.dp,
                        color = if (isFocused) {
                            if (!isEnabled) {
                                MaterialTheme.colorScheme.tertiary
                            } else if (isError) {
                                MaterialTheme.colorScheme.error
                            } else {
                                MaterialTheme.colorScheme.primary
                            }
                        } else {
                            Color.Transparent
                        },
                        shape = MaterialTheme.shapes.medium,
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
                maxLines = 1,
                placeholder = {
                    Text(
                        text = placeholderText,
                        style = textStyle,
                    )
                },
                keyboardOptions = keyboardOptions,
                textStyle = MaterialTheme.typography.bodyMedium,
                shape = MaterialTheme.shapes.medium,
                visualTransformation = if (isVisible) {
                    VisualTransformation.None
                } else {
                    PasswordVisualTransformation('*')
                },
                trailingIcon = {
                    Icon(
                        painter = painterResource(
                            id = if (isVisible) {
                                BanklyIcons.VisibilityOff
                            } else {
                                BanklyIcons.VisibilityOn
                            },
                        ),
                        contentDescription = "Visibility Icon",
                        modifier = Modifier
                            .size(24.dp)
                            .clip(MaterialTheme.shapes.small)
                            .clickable(
                                onClick = {
                                    isVisible = !isVisible
                                },
                                enabled = isEnabled,
                                role = Role.Button,
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(
                                    bounded = true,
                                    color = if (isError) {
                                        MaterialTheme.colorScheme.error
                                    } else {
                                        MaterialTheme.colorScheme.primary
                                    },
                                ),
                            ),
                        tint = if (isError) {
                            MaterialTheme.colorScheme.error
                        } else {
                            MaterialTheme.colorScheme.primary
                        },
                    )
                },
                colors = textFieldColors,
            )
        } else {
            TextField(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = horizontalPadding, vertical = 8.dp)
                    .border(
                        width = 1.dp,
                        color = if (isFocused) {
                            if (!isEnabled) {
                                MaterialTheme.colorScheme.tertiary
                            } else if (isError) {
                                MaterialTheme.colorScheme.error
                            } else {
                                MaterialTheme.colorScheme.primary
                            }
                        } else {
                            Color.Transparent
                        },
                        shape = MaterialTheme.shapes.medium,
                    )
                    .onFocusChanged { focusState ->
                        isFocused = focusState.isFocused
                    },
                value = textFieldValue,
                placeholder = {
                    Text(
                        text = placeholderText,
                        style = textStyle,
                    )
                },
                visualTransformation = visualTransformation,
                onValueChange = onTextFieldValueChange,
                enabled = isEnabled,
                readOnly = readOnly,
                isError = isError,
                singleLine = true,
                maxLines = 1,
                keyboardOptions = keyboardOptions,
                textStyle = textStyle,
                shape = MaterialTheme.shapes.medium,
                colors = textFieldColors,
                trailingIcon = {
                    trailingIcon?.let {
                        Icon(
                            painter = painterResource(trailingIcon),
                            contentDescription = "Trailing icon",
                            modifier = Modifier
                                .size(24.dp)
                                .clip(MaterialTheme.shapes.small)
                                .clickable(
                                    onClick = onTrailingIconClick,
                                    enabled = isEnabled,
                                    role = Role.Button,
                                    interactionSource = remember { MutableInteractionSource() },
                                    indication = rememberRipple(
                                        bounded = true,
                                        color = if (isError) {
                                            MaterialTheme.colorScheme.error
                                        } else {
                                            MaterialTheme.colorScheme.primary
                                        },
                                    ),
                                ),
                            tint = Color.Unspecified,
                        )
                    }
                },
            )
        }
        Text(
            text = feedbackText,
            style = MaterialTheme.typography.bodySmall.copy(
                color = if (!isEnabled) {
                    MaterialTheme.colorScheme.tertiary
                } else if (isError) {
                    MaterialTheme.colorScheme.error
                } else {
                    MaterialTheme.colorScheme.primary
                },
            ),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier.padding(horizontal = horizontalPadding),
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.white)
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
@Preview(showBackground = true, backgroundColor = PreviewColor.white)
private fun BanklyInputFieldPreview2() {
    BanklyTheme {
        BanklyInputField(
            textFieldValue = TextFieldValue(text = ""),
            onTextFieldValueChange = {},
            labelText = "Passcode",
            placeholderText = "Input password",
            isPasswordField = true,
            isError = true,
            feedbackText = "Invalid input",
        )
    }
}

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.white)
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
@Preview(showBackground = true, backgroundColor = PreviewColor.white)
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

@Composable
@Preview(showBackground = true, backgroundColor = PreviewColor.white)
private fun BanklyInputFieldPreview5() {
    BanklyTheme {
        BanklyInputField(
            textFieldValue = TextFieldValue(text = "Hassan Abdulwahab"),
            onTextFieldValueChange = {},
            placeholderText = "What's your name?",
            isEnabled = false,
        )
    }
}
