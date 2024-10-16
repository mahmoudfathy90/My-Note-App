package com.example.mvitestapplication.presentation.state

import com.example.mvitestapplication.data.entity.Note

data class NoteState(
    val notes: List<Note> = emptyList(),
    val isLoading: Boolean = false
)