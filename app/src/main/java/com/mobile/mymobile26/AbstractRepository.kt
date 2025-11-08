package com.mobile.com.mobile.mymobile26

import android.database.sqlite.SQLiteConstraintException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

abstract class AbstractRepository {
    abstract val allFlashCards: Flow<List<FlashCard>>

    abstract suspend fun insert(flashCard: FlashCard)
    abstract suspend fun delete(flashCard: FlashCard)
    abstract fun get(flashCardId: Int): Flow<FlashCard?>






}
