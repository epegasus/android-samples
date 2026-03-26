package com.sohaib.roomdatabase.data.dataSource

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sohaib.roomdatabase.data.dataSource.daos.NoteDao
import com.sohaib.roomdatabase.data.dataSource.model.Note

@Database(entities = [Note::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    // List all Dao
    abstract fun noteDao(): NoteDao

    companion object {
        private const val DATABASE_NAME: String = "notes_database.db"
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                if (INSTANCE == null) {
                    val instance = Room.databaseBuilder(
                        context.applicationContext,
                        AppDatabase::class.java,
                        DATABASE_NAME
                    ).build()
                    INSTANCE = instance
                }
                INSTANCE as AppDatabase
            }
        }
    }
}