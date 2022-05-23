package com.example.autocompletetext.utils

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.autocompletetext.component.SearchTextField
import com.example.autocompletetext.model.SampleData
import java.util.*
import kotlin.collections.ArrayList

@ExperimentalAnimationApi
@Composable
fun AutoCompleteName(sampleData: List<SampleData>) {
    val name = ArrayList<String>()

    if (sampleData.isNotEmpty()) {
        sampleData.forEach { sampleData ->
            name.add(sampleData.name)
        }
    }

    val autoCompleteEntities = name.isAutoCompleteEntities(
        filter = { item, query ->
            item.lowercase(Locale.getDefault()).startsWith(query.lowercase(Locale.getDefault()))
        }
    )

    AutoCompleteBox(
        items = autoCompleteEntities,
        itemContent = { item ->
            NameAutoCompleteItem(item.value)
        }
    ) {
        var value by remember { mutableStateOf("") }
        val view = LocalView.current

        onItemSelected { item ->
            value = item.value
            filter(value)
            view.clearFocus()
        }

        SearchTextField(
            value = value,
            label = "Search Name",
            onValueChanged = { query ->
                value = query
                filter(value)
            },
            onDoneActionClick = {
                view.clearFocus()
            },
            onClearClick = {
                value = ""
                filter(value)
                view.clearFocus()
            },
            onFocusChanged = { focusState ->
                if (focusState.isFocused) {
                    isSearching = true
                }
            },
            modifier = Modifier.testTag("AutoCompleteSearchBar")
        )
    }
}

@Composable
fun NameAutoCompleteItem(value: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(15.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = value,
            fontSize = 15.sp,
            color = Color.Black
        )
    }
}

@OptIn(ExperimentalAnimationApi::class)
@Preview
@Composable
fun AutoCompleteNamePreview() {
    AutoCompleteName(sampleData = listOf())
}