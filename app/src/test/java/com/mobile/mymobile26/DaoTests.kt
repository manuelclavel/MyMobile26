package com.mobile.mymobile26


import android.content.Context
import android.database.sqlite.SQLiteConstraintException
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.mobile.com.mobile.mymobile26.AnNamDatabase
import com.mobile.com.mobile.mymobile26.FlashCard
import com.mobile.com.mobile.mymobile26.FlashCardDao
import kotlinx.coroutines.runBlocking
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
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AnNamDatabase::class.java).build()
        flashCardDao = db.flashCardDao()
    }

    @After
    fun closeDb() {
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

        var result = FlashCard(0, "", "")

        runBlocking {
            flashCardDao.insertAll(flashCard)
        }

        runBlocking {
            result = flashCardDao.findByCards("test_english", "test_vietnamese")
        }
        Assert.assertEquals(result.englishCard, flashCard.englishCard)
        Assert.assertEquals(result.vietnameseCard, flashCard.vietnameseCard)
    }

    @Test
    fun insertFlashCardUSuccessful() {
        val flashCard =
            FlashCard(
                uid = 0,
                englishCard = "test_english",
                vietnameseCard = "test_vietnamese"
            )

            runBlocking {
                flashCardDao.insertAll(flashCard)
            }
            var error = false
            runBlocking {
            try {
                flashCardDao.insertAll(flashCard)
            } catch (e: SQLiteConstraintException){
                error = true
            }
        }
        Assert.assertEquals(true, error)
    }
}


