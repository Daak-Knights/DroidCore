package com.daakknights.room

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface DreamDao {
    @Query("SELECT * FROM bucket_list_table ORDER BY dream ASC")
    fun getSortedDream(): Flow<List<Dream>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(dream: Dream)

    @Query("DELETE FROM bucket_list_table")
    suspend fun deleteAll()

    @Delete()
    suspend fun delete(dream: Dream)

    @Update
    suspend fun update(dream: Dream)
}