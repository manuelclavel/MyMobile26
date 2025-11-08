package com.mobile.com.mobile.mymobile26

import android.database.sqlite.SQLiteConstraintException
import kotlinx.coroutines.flow.Flow
import android.util.Log

class FlashCardRepository(private val flashCardDao: FlashCardDao
): AbstractRepository() {

    override val allFlashCards: Flow<List<FlashCard>> = flashCardDao.getAll()

    override  fun get(english: String?, vietnamese: String?): Flow<FlashCard?> {
        Log.d("AnNam", "trying to get the flash card from database")
        //try {
            return flashCardDao.findByCard(english, vietnamese)
        //} catch (e: SQLiteConstraintException) {
        //    Log.d("AnNam", "Error getting flash cards: ${e.message}")
        //    throw SQLiteConstraintException(e.message)
        //} catch (e: Exception) {
        //    Log.d("AnNam", "Error getting flash cards: ${e.message}")
        //    throw Exception(e.message)
        //}
    }


    override suspend fun insert(flashCard: FlashCard) {
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



    override suspend fun delete(flashCard: FlashCard) {
        flashCardDao.delete(flashCard)
    }


}