package ru.gureva.ebookreader.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.Date

@Entity(tableName = "books")
data class BookEntity(
    @PrimaryKey val fileUrl: String,
    val title: String,
    val author: String,
    @ColumnInfo(name = "creation_date")
    val creationDate: Date
)
