package com.mobile.com.mobile.mymobile26

import androidx.lifecycle.LiveData
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
import androidx.room.RenameTable
import androidx.room.RoomDatabase
import androidx.room.migration.AutoMigrationSpec
import kotlinx.coroutines.flow.Flow


@Entity(tableName = "FlashCards", indices = [Index(value = ["english_card","vietnamese_card"],
    unique = true)])
data class FlashCard(
    @PrimaryKey(autoGenerate = true) val uid: Int,
    @ColumnInfo(name = "english_card") val englishCard: String?,
    @ColumnInfo(name = "vietnamese_card") val vietnameseCard: String?,
)

@Dao
interface FlashCardDao {
    //Define your Room DAO method to return LiveData<List<YourEntity>>.
    // Room automatically handles updating this LiveData
    // when the underlying database data changes.

    @Query("SELECT * FROM FlashCards")
    fun getAll(): Flow<List<FlashCard>>

    @Query("SELECT * FROM FlashCards WHERE english_card LIKE :english AND " +
            "vietnamese_card LIKE :vietnamese LIMIT 1")
    suspend  fun findByCard(english: String, vietnamese: String): FlashCard

    @Insert
    suspend fun insert(flashCard: FlashCard)

    @Delete
    suspend fun delete(flashCard: FlashCard)
}

@Database(entities = [FlashCard::class], version = 1)
abstract class AnNamDatabaseTest : RoomDatabase() {
    abstract fun flashCardDaoTest(): FlashCardDao
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

/*
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
*/
@Database(
    version = 4,
    entities = [FlashCard::class],
    autoMigrations = [
        AutoMigration (
            from = 3,
            to = 4,
            spec = AnNamDatabase.MyAutoMigration::class
        )
    ]
)
abstract class AnNamDatabase : RoomDatabase() {
    abstract fun flashCardDao(): FlashCardDao
    @RenameTable(fromTableName = "FlashCard", toTableName = "FlashCards")
    class MyAutoMigration : AutoMigrationSpec {}
}
