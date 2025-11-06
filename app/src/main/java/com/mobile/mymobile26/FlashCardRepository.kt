package com.mobile.com.mobile.mymobile26

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow

class FlashCardRepository(private val flashCardDao: FlashCardDao) {
    //suspend fun getAll(): List<FlashCard> {
    //    return flashCardDao.getAll()
    //}
    val allFlashCards: Flow<List<FlashCard>> = flashCardDao.getAll()

    suspend fun insert(flashCard: FlashCard) {
        flashCardDao.insert(flashCard)
    }

    suspend fun delete(flashCard: FlashCard) {
        flashCardDao.delete(flashCard)
    }
}