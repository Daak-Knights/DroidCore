package com.daakknights.room

import android.app.Application
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class RoomApplication : Application() {
    private val applicationScope = CoroutineScope(SupervisorJob())
    private val database by lazy { DreamRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { DreamRepository(database.dreamDao()) }
}