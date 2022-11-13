package com.example.juvenilediabetesmanager

import android.content.Context
import androidx.room.*

@Database(entities = [Entry::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun entryDao(): EntryDao

    companion object {
        private var INSTANCE: AppDatabase? = null
        fun getDatabase(context: Context):AppDatabase {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE =
                        Room.databaseBuilder(context, AppDatabase::class.java, "app_database").build()
                }
            }
            return INSTANCE!!
        }
    }

}