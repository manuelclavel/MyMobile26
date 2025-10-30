package com.mobile.com.mobile.mymobile26

import androidx.room.AutoMigration
import androidx.room.ColumnInfo
import androidx.room.Dao
import androidx.room.Database
import androidx.room.Delete
import androidx.room.Entity
import androidx.room.Index
import androidx.room.Insert
import androidx.room.PrimaryKey
import androidx.room.Query
import androidx.room.RoomDatabase


@Entity(indices = [Index(value = ["english_card","vietnamese_card"], unique = true)])
data class FlashCard(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "english_card") val englishCard: String?,
    @ColumnInfo(name = "vietnamese_card") val vietnameseCard: String?
)

@Dao
interface FlashCardDao {
    @Query("SELECT * FROM FlashCard")
    fun getAll(): List<FlashCard>

    @Query("SELECT * FROM FlashCard WHERE uid IN (:flashCardIds)")
    fun loadAllByIds(flashCardIds: IntArray): List<FlashCard>

    @Query("SELECT * FROM FlashCard WHERE english_card LIKE :english AND " +
            "vietnamese_card LIKE :vietnamese LIMIT 1")
    fun findByCards(english: String, vietnamese: String): FlashCard

    @Insert
    fun insertAll(vararg flashCard: FlashCard)

    @Delete
    fun delete(flashCard: FlashCard)
}

// Database class after the version update.
/*
@Database(
    version = 2,
    entities = [FlashCard::class],
    autoMigrations = [
        AutoMigration (from = 1, to = 2)
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun flashCardDao(): FlashCardDao
}
*/
@Database(
    version = 3,
    entities = [FlashCard::class],
    autoMigrations = [
        AutoMigration (from = 2, to = 3)
    ]
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun flashCardDao(): FlashCardDao
}
