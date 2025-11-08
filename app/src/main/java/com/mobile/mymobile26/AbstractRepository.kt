package com.mobile.com.mobile.mymobile26

import kotlinx.coroutines.flow.Flow

abstract class AbstractRepository {
    abstract val allFlashCards: Flow<List<FlashCard>>

    abstract suspend fun insert(flashCard: FlashCard)
    abstract suspend fun delete(flashCard: FlashCard)
    abstract fun get(english: String?, vietnamese: String?): Flow<FlashCard?>






}
