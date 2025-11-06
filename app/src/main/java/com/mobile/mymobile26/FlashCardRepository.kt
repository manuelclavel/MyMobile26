package com.mobile.com.mobile.mymobile26

import android.database.sqlite.SQLiteConstraintException
import kotlinx.coroutines.flow.Flow

class FlashCardRepository(private val flashCardDao: FlashCardDao) {
    //suspend fun getAll(): List<FlashCard> {
    //    return flashCardDao.getAll()
    //}
    val allFlashCards: Flow<List<FlashCard>> = flashCardDao.getAll()

    suspend fun insert(flashCard: FlashCard) {
        try {
            flashCardDao.insert(flashCard)
        } catch (e: SQLiteConstraintException) {
           throw SQLiteConstraintException(e.message)
            //Log.d("AnNam", "Error getting flash cards: ${e.message}")
        } catch (e: Exception) {
            throw Exception(e.message)
            //Log.d("AnNam", "Error getting flash cards: ${e.message}")
        }

    }

    suspend fun delete(flashCard: FlashCard) {
        flashCardDao.delete(flashCard)
    }
}