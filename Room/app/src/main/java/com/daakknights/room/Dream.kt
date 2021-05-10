package com.daakknights.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bucket_list_table")
data class Dream(
    @ColumnInfo(name = "dream") val dream: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
