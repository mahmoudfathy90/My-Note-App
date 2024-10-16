package com.example.mvitestapplication.presentation.viewmodel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mvitestapplication.data.entity.Note
import com.example.mvitestapplication.data.repository.NoteRepository
import com.example.mvitestapplication.presentation.intent.NoteIntent
import com.example.mvitestapplication.presentation.state.NoteState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NoteViewModel @Inject constructor(
    private val repository: NoteRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(NoteState())
    val uiState: StateFlow<NoteState> = _uiState

    init {
        loadNotes()
    }

    fun handleIntent(intent: NoteIntent) {
        when (intent) {
            is NoteIntent.LoadNotes -> loadNotes()
            is NoteIntent.AddNote -> addNote(intent.title, intent.content)
            is NoteIntent.DeleteNote -> deleteNote(intent.id)
        }
    }

    private fun loadNotes() {
        repository.getAllNotes().onEach { notes ->
            _uiState.value = _uiState.value.copy(notes = notes, isLoading = false)
        }.launchIn(viewModelScope)
    }

    private fun addNote(title: String, content: String) {
        viewModelScope.launch {
            repository.insertNote(Note(title = title, content = content))
        }
    }

    private fun deleteNote(id: Long) {
        viewModelScope.launch {
            val noteToDelete = _uiState.value.notes.find { it.id == id }
            noteToDelete?.let { repository.deleteNote(it) }
        }
    }
}
