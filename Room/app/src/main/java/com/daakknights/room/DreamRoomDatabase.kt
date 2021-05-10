package com.daakknights.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Dream::class), version = 1, exportSchema = false)
public abstract class DreamRoomDatabase : RoomDatabase() {

    abstract fun dreamDao(): DreamDao

    companion object {
        @Volatile
        private var INSTANCE: DreamRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): DreamRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DreamRoomDatabase::class.java,
                    "bucket_list_database"
                ).addCallback(DreamDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }

    private class DreamDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            INSTANCE?.let { database ->
                scope.launch {
                    populateDatabase(database.dreamDao())
                }
            }
        }

        suspend fun populateDatabase(dreamDao: DreamDao) {
            // Delete all content here.
            dreamDao.deleteAll()
            var dream = Dream(dream = "Want to have a pet dog")
            dreamDao.insert(dream)
            dream = Dream(dream = "Wanna learn swimming")
            dreamDao.insert(dream)

        }
    }
}