package com.daakknights.room

import androidx.annotation.WorkerThread
import kotlinx.coroutines.flow.Flow

class DreamRepository(private val dreamDao: DreamDao) {
    val allDreams: Flow<List<Dream>> = dreamDao.getSortedDream()

    @WorkerThread
    suspend fun insert(dream: Dream) {
        dreamDao.insert(dream)
    }

    @WorkerThread
    suspend fun delete(dream: Dream) {
        dreamDao.delete(dream)
    }
}