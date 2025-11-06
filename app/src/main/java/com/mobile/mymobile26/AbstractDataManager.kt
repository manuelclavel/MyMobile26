package com.mobile.com.mobile.mymobile26

import kotlinx.coroutines.Deferred


abstract class AbstractDataManager {
    abstract val addFlashCard : (String, String, (Int)->Unit) -> Unit
    abstract val getFlashCards : () -> List<FlashCard>
}
