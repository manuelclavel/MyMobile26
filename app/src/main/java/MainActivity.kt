package com.mobile

import Navigator
import android.database.sqlite.SQLiteConstraintException
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.mobile.com.mobile.mymobile26.AppDatabase
import com.mobile.com.mobile.mymobile26.FlashCard
import com.mobile.com.mobile.mymobile26.FlashCardDao
import com.mobile.mymobile26.ui.theme.MyMobile26Theme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

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


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        //val db = AppDatabaseSingleton.getInstance(
        //    context = applicationContext
        //)


        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val db = Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java, "MyMobile26Database"
            ).build()
            val flashCardDao = db.flashCardDao()

            val getAllFlashCards =  fun(): List<FlashCard> {
                var flashCards : List<FlashCard> = emptyList()
                runBlocking {
                    async {
                        withContext(Dispatchers.IO) {
                            try {
                                flashCards = flashCardDao.getAll()
                                Log.d("MANU", flashCards.toString())
                            } catch (e: SQLiteConstraintException) {
                                // Handle specific Room exceptions like unique constraint violation
                                // Log the error or show a user-friendly message
                                Log.d("MANU", "Error getting flash cards: ${e.message}")
                            } catch (e: Exception) {
                                // Catch any other unexpected exceptions
                                Log.d("MANU", "An unexpected error occurred: ${e.message}")
                            }
                        }
                    }.await()
                }
                return flashCards
            }

            val addFlashCard =  fun(english:String, vietnamese: String): Int {
                var code = 0
                runBlocking {
                    async {
                        withContext(Dispatchers.IO) {
                            try {
                                flashCardDao.insertAll(
                                    FlashCard(
                                        uid = 0,
                                        englishCard = english,
                                        vietnameseCard = vietnamese
                                    )
                                )
                                code = 200
                            } catch (e: SQLiteConstraintException) {
                                // Handle specific Room exceptions like unique constraint violation
                                // Log the error or show a user-friendly message
                                code = 501
                                Log.d("MANU", "Error inserting user: ${e.message}")
                            } catch (e: Exception) {
                                // Catch any other unexpected exceptions
                                code = 500
                                Log.d("MANU", "An unexpected error occurred: ${e.message}")
                            }
                        }
                    }.await()
                }
                return code
            }



            MyMobile26Theme {
                Navigator(navController, getAllFlashCards, addFlashCard)
            }
        }
    }
}