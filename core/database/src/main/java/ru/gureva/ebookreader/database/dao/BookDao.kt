package ru.gureva.ebookreader.database.dao

import androidx.room.Dao
import androidx.room.Insert
import ru.gureva.ebookreader.database.entity.BookEntity

@Dao
interface BookDao {
    @Insert
    fun insert(bookEntity: BookEntity)
}
