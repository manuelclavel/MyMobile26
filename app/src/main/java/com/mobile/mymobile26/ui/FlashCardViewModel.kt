package com.mobile.com.mobile.mymobile26.ui

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.compose.foundation.rememberPlatformOverscrollFactory
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.mobile.com.mobile.mymobile26.AnNamDatabase
import com.mobile.com.mobile.mymobile26.FlashCard
import com.mobile.com.mobile.mymobile26.FlashCardRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FlashCardViewModel(private val repository: FlashCardRepository): ViewModel() {
    val allFlashCards: StateFlow<List<FlashCard>> = repository.allFlashCards
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
    fun insertFlashCard(flashCard: FlashCard) {
        viewModelScope.launch {
            repository.insert(flashCard)
        }
    }

    fun deleteItem(flashCard: FlashCard) {
        viewModelScope.launch {
            repository.delete(flashCard)
        }
    }




    /*
    Encouraging Correct State Management: Using val for MutableState variables
    promotes a cleaner and more predictable state management pattern.
    It signals that the identity of the state object is stable,
    while its contents are subject to change.
    This aligns with Composes recomposition model, where changes to
    observable state values trigger UI updates.
    In essence, val ensures that you are always working with the same
    MutableState instance, while MutableState itself provides the mechanism
    for modifying the actual data it holds, triggering recompositions
    when necessary.
     */
    private val _uiState = MutableStateFlow(FlashCardUiState())
    val uiState: StateFlow<FlashCardUiState> = _uiState.asStateFlow()


   private fun resetMessage() {
        _uiState.value = FlashCardUiState(
            currentMessage = "Copyright © Mobile Programming, 2025")
   }

    fun updateCurrentMessage(text: String){
        _uiState.value = FlashCardUiState(currentMessage = text)
    }


    init {
        resetMessage()
    }
}