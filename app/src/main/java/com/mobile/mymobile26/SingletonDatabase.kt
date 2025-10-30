package com.mobile.com.mobile.mymobile26

import android.content.Context
import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.Room


@Database(entities = [FlashCard::class], version = 1, exportSchema = true)
abstract class AppDatabaseSingleton : RoomDatabase() {

    abstract fun flashCarDao(): FlashCardDao // Replace YourDao and YourEntity with your actual DAO and Entity

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            // Use synchronized to ensure only one thread can access this block at a time
            return INSTANCE ?: synchronized(this) {
                // Check again inside the synchronized block to prevent multiple instances
                // if multiple threads pass the first check simultaneously
                INSTANCE ?: Room.databaseBuilder(
                                context.applicationContext,
                                AppDatabase::class.java,
                                "AnNamDatabase" // Replace with your desired database name
                                ).fallbackToDestructiveMigration(false) // Optional: handle schema changes by rebuilding the database
                    .build()
                    .also { INSTANCE = it } // Assign the built instance to INSTANCE
            }
        }
    }
}

