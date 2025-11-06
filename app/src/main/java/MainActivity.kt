package com.mobile

import Navigator
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.mobile.com.mobile.mymobile26.AbstractDataManager
import com.mobile.com.mobile.mymobile26.AnNamDatabase
import com.mobile.com.mobile.mymobile26.FlashCard
import com.mobile.com.mobile.mymobile26.FlashCardRepository
import com.mobile.com.mobile.mymobile26.ui.FlashCardViewModel
import com.mobile.mymobile26.ui.theme.MyMobile26Theme
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

/*
suspend fun getAll(dao: FlashCardDao):List<FlashCard> {
    var flashCards : List<FlashCard> = emptyList()
    withContext(Dispatchers.IO) {
        async {
            flashCards = dao.getAll()
        }.await()
    }
    return flashCards
}

suspend fun insertAll(dao: FlashCardDao, vararg flashCards: FlashCard) {
    withContext(Dispatchers.IO) {
        async {
            dao.insertAll(*flashCards)
        }.await()
    }
}
*/


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val db = Room.databaseBuilder(
                applicationContext,
                AnNamDatabase::class.java, "MyMobile26Database"
            ).build()

            val flashCardDao = db.flashCardDao()
            val viewModel = FlashCardViewModel(
                repository = FlashCardRepository(
                    flashCardDao = flashCardDao
                )
            )
            //runBlocking {
                //flashCardDao.insertAll(FlashCard(
                //    uid = 0,
                //    englishCard = "test16",
                //    vietnameseCard = "test17"
                //))
            //    val flashCards = flashCardDao.getAll()
            //    Log.d("AnNam", flashCards.toString())
            //}

            //val getFlashCards = fun (): List<FlashCard> {
            //    var flashCards : List<FlashCard> = emptyList()
            //    runBlocking {
            //        async {
            //            withContext(Dispatchers.IO) {
            //                try {
            //                    flashCards = flashCardDao.getAll()
            //                    Log.d("MANU", flashCards.toString())
            //                } catch (e: SQLiteConstraintException) {
                                // Handle specific Room exceptions like unique constraint violation
                                // Log the error or show a user-friendly message
            //                    Log.d("MANU", "Error getting flash cards: ${e.message}")
            //                } catch (e: Exception) {
                                // Catch any other unexpected exceptions
            //                    Log.d("MANU", "An unexpected error occurred: ${e.message}")
            //                }
            //            }
            //        }.await()
            //    }
            //    return flashCards
            //}
            //val scope = rememberCoroutineScope()

            //val addFlashCard = fun(english: String, vietnamese: String, result: (Int)-> Unit): Unit {
            //    scope.async {
            //        try {
            //            flashCardDao.insert(
            //                FlashCard(
            //                    uid = 0,
            //                    englishCard = english,
            //                    vietnameseCard = vietnamese
            //                )
            //            )
            //            result(200)
            //        } catch (e: SQLiteConstraintException) {
                        // Handle specific Room exceptions like unique constraint violation
                        // Log the error or show a user-friendly message
            //            result(501)
            //            Log.d("MANU", "Error inserting user: ${e.message}")
            //        } catch (e: Exception) {
                        // Catch any other unexpected exceptions
            //            result(500)
            //            Log.d("MANU", "An unexpected error occurred: ${e.message}")
            //        }
            //    }
            //}

            MyMobile26Theme {
                Navigator(navController, viewModel)
            }
        }
    }
}