package com.mobile

import Navigator
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.mobile.com.mobile.mymobile26.AppDatabase
import com.mobile.com.mobile.mymobile26.AppDatabaseSingleton
import com.mobile.com.mobile.mymobile26.FlashCard
import com.mobile.com.mobile.mymobile26.FlashCardDao
import com.mobile.mymobile26.ui.theme.MyMobile26Theme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
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

            MyMobile26Theme {
                Navigator(navController,flashCardDao)
            }
        }
    }
}