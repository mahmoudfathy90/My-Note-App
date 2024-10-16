package com.example.mvitestapplication.presentation.intent

sealed class NoteIntent {
    data object LoadNotes : NoteIntent()
    data class AddNote(val title: String, val content: String) : NoteIntent()
    data class DeleteNote(val id: Long) : NoteIntent()
}