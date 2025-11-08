package com.mobile.com.mobile.mymobile26

import android.util.Log
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics

@Composable
fun ShowCardScreen(flashCard: FlashCard?,
                   deleteFlashCard: (FlashCard) -> Unit) {


    Column() {
        TextField(
                value = flashCard?.englishCard.orEmpty(),
                onValueChange = { },
                modifier = Modifier.semantics { contentDescription = "enTextField" },
                label = { Text("en") },
                readOnly = true // Set to true for read-only behavior
            )
        TextField(
                value = flashCard?.vietnameseCard.orEmpty(),
                onValueChange = {  },
                modifier = Modifier.semantics { contentDescription = "vnTextField" },
                label = { Text("vn") },
                readOnly = true // Set to true for read-only behavior
            )

        Button(
            modifier = Modifier.semantics { contentDescription = "Add" },
            onClick = {
                if (flashCard != null) {
                    deleteFlashCard(flashCard)
                }
            })
        {
            Text("Delete")
        }
    }
}
