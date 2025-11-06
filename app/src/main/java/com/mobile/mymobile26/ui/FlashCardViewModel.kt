package com.mobile.com.mobile.mymobile26.ui

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobile.R
import com.mobile.com.mobile.mymobile26.FlashCard
import com.mobile.com.mobile.mymobile26.FlashCardRepository
import com.mobile.com.mobile.mymobile26.FlashCardResourceProvider
import com.mobile.com.mobile.mymobile26.ResourceProvider
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class FlashCardViewModel(
    private val resourceProvider: FlashCardResourceProvider,
    private val repository: FlashCardRepository): ViewModel() {

    fun getMessageAddSuccessful(): String {
       return resourceProvider.getString(R.string.add_successful)
   }
    fun getMessageAddUnSuccessful(): String {
        return resourceProvider.getString(R.string.add_unsuccessful)
    }
    val allFlashCards: StateFlow<List<FlashCard>> = repository.allFlashCards
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )
    fun insertFlashCard(flashCard: FlashCard) {
        viewModelScope.launch {
            try {
                repository.insert(flashCard)
                updateCurrentMessage(getMessageAddSuccessful())
                updateAddEnWord("")
                updateAddVnWord("")
            } catch (e: SQLiteConstraintException) {
                //throw SQLiteConstraintException(e.message)
                updateCurrentMessage(getMessageAddUnSuccessful())
                Log.d("AnNam", "Error getting flash cards: ${e.message}")
            } catch (e: Exception) {
                // Catch any other unexpected exceptions
                //throw Exception(e.message)
                updateCurrentMessage("Error getting flash cards: ${e.message}")
                Log.d("AnNam", "Error getting flash cards: ${e.message}")
            }
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


   private fun resetFlashCardState() {
        _uiState.value = FlashCardUiState(
            currentMessage = "",
            currentEnWord = "",
            currentVnWord = ""
        )

   }

    fun updateCurrentMessage(text: String){
        _uiState.value = _uiState.value.copy(currentMessage = text)
    }

    fun updateAddEnWord(text:String){
        _uiState.value =
            _uiState.value.copy(currentEnWord = text)
    }

    fun updateAddVnWord(text:String){
        _uiState.value =
            _uiState.value.copy(currentVnWord = text)
    }

    init {
        resetFlashCardState()
    }
}