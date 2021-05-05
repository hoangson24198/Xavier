package com.hoangson.xavier.core.compose.layout

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusState
import androidx.compose.ui.focus.isFocused
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.SoftwareKeyboardController
import androidx.compose.ui.semantics.SemanticsPropertyKey
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
fun SearchInput(hint: String, textState: MutableState<TextFieldValue>, onValueChange: (String) -> Unit) {
    // Used to decide if the keyboard should be shown
    var textFieldFocusState by remember { mutableStateOf(false) }
    UserInputText(
        textFieldValue = textState.value,
        onTextChanged = {
            textState.value = it
            onValueChange.invoke(it.text)
        },
        // Only show the keyboard if there's no input selector and text field has focus
        keyboardShown = textFieldFocusState,
        // Close extended selector if text field receives focus
        onTextFieldFocused = { focused ->
            textFieldFocusState = focused
        },
        hint = hint,
        focusState = textFieldFocusState
    )
}

val KeyboardShownKey = SemanticsPropertyKey<Boolean>("KeyboardShownKey")
var SemanticsPropertyReceiver.keyboardShownProperty by KeyboardShownKey

@ExperimentalComposeUiApi
@ExperimentalFoundationApi
@Composable
private fun UserInputText(
    keyboardType: KeyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
    onTextChanged: (TextFieldValue) -> Unit,
    textFieldValue: TextFieldValue,
    keyboardShown: Boolean,
    hint: String = "",
    onTextFieldFocused: (Boolean) -> Unit,
    focusState: Boolean
) {
    // Grab a reference to the keyboard controller whenever text input starts
    var keyboardController by remember { mutableStateOf<SoftwareKeyboardController?>(null) }

    // Show or hide the keyboard
    SideEffect {
        keyboardController?.let {
            if (keyboardShown) it.show() else it.hide()
        }
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .size(48.dp)
            .semantics {
                keyboardShownProperty = keyboardShown
            },
        horizontalArrangement = Arrangement.End
    ) {
        Box(
            modifier = Modifier.size(45.dp).weight(1f).align(Alignment.Bottom)
        ) {
            var lastFocusState by remember { mutableStateOf(FocusState.Inactive) }
            TextField(
                value = textFieldValue,
                onValueChange = {onTextChanged(textFieldValue)},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp)
                    .align(Alignment.CenterStart)
                    .onFocusChanged { state ->
                        if (lastFocusState != state) {
                            onTextFieldFocused(state.isFocused)
                        }
                        lastFocusState = state
                    },
                keyboardOptions = keyboardType,
                textStyle = MaterialTheme.typography.body2
            )
            val disableContentColor =
                AmbientEmphasisLevels.current.disabled.applyEmphasis(MaterialTheme.colors.onSurface)
            if (textFieldValue.text.isEmpty() && !focusState) {
                Text(
                    modifier = Modifier
                        .align(Alignment.CenterStart)
                        .padding(start = 16.dp),
                    text = hint,
                    style = MaterialTheme.typography.body2.copy(color = disableContentColor)
                )
            }
        }
    }
}
