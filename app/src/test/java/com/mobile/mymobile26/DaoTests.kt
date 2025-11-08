package com.mobile.mymobile26


import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.mobile.com.mobile.mymobile26.AnNamDatabase
import com.mobile.com.mobile.mymobile26.FlashCard
import com.mobile.com.mobile.mymobile26.FlashCardDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner


@RunWith(RobolectricTestRunner::class)
class DaoTest {
    @get:Rule
    private lateinit var db: AnNamDatabase
    private lateinit var flashCardDao: FlashCardDao

    
    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AnNamDatabase::class.java).build()
        flashCardDao = db.flashCardDao()
    }

    @After
    fun close(){
        db.close()
    }



    // ... test methods
    @Test
    fun insertFlashCardSuccessful() {

        val flashCard =
            FlashCard(
                uid = 0,
                englishCard = "test_english",
                vietnameseCard = "test_vietnamese"
            )


        runBlocking {
            flashCardDao.insert(flashCard)
        }

        val item:FlashCard?
        runBlocking {
            val outflow: Flow<FlashCard?> = flashCardDao.findByCard("test_english", "test_vietnamese")
            item = outflow.first()
        }
        Assert.assertEquals(flashCard.englishCard, item?.englishCard, )
        Assert.assertEquals(flashCard.vietnameseCard, item?.vietnameseCard)


    }

    @Test
    fun insertFlashCardUnSuccessful() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AnNamDatabase::class.java).build()
        flashCardDao = db.flashCardDao()

        val flashCard =
            FlashCard(
                uid = 0,
                englishCard = "test_english",
                vietnameseCard = "test_vietnamese"
            )

            runBlocking {
                flashCardDao.insert(flashCard)
            }
            var error = false
            runBlocking {
            try {
                flashCardDao.insert(flashCard)
            } catch (e: SQLiteConstraintException){
                error = true
                }
            }
            Assert.assertEquals(true, error)
         db.close()
    }
}


