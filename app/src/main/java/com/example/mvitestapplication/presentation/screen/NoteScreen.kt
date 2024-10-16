package com.example.mvitestapplication.presentation.screen

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mvitestapplication.data.entity.Note
import com.example.mvitestapplication.presentation.intent.NoteIntent
import com.example.mvitestapplication.presentation.viewmodel.NoteViewModel


@Composable
fun NoteScreen(viewModel: NoteViewModel = hiltViewModel() ) {
    val uiState by viewModel.uiState.collectAsState()

    var title by remember { mutableStateOf("") }
    var content by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        LazyColumn(
            modifier = Modifier.weight(1f)
        ) {
            items(uiState.notes) { note ->
                NoteItem(note = note, onDeleteClick = { viewModel.handleIntent(NoteIntent.DeleteNote(note.id)) })
            }
        }

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Title") },
            modifier = Modifier.fillMaxWidth()
        )
        OutlinedTextField(
            value = content,
            onValueChange = { content = it },
            label = { Text("Content") },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = {
                viewModel.handleIntent(NoteIntent.AddNote(title, content))
                title = ""
                content = ""
            }
        ) {
            Text("Add Note")
        }
    }
}

@Composable
fun NoteItem(note: Note, onDeleteClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Column {
            Text(note.title, modifier = Modifier.padding(bottom = 4.dp))
            Text(note.content)
        }
        Button(onClick = onDeleteClick) {
            Text("Delete")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NoteScreenPreview() {
    NoteScreen()
}
