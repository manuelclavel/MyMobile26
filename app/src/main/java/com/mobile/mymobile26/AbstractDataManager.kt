package com.mobile.com.mobile.mymobile26


abstract class AbstractDataManager {
    abstract val addFlashCard : (String, String) -> Int
    abstract val getFlashCards : () -> List<FlashCard>
}
