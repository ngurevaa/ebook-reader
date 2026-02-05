package ru.gureva.ebookreader.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.gureva.ebookreader.database.entity.BookEntity

@Dao
interface BookDao {
    @Insert
    suspend fun insert(bookEntity: BookEntity)

    @Query("SELECT * FROM books")
    fun getAll(): Flow<List<BookEntity>>

    @Upsert
    suspend fun upsertAll(books: List<BookEntity>)

    @Query("DELETE FROM books WHERE fileName NOT IN (:ids)")
    suspend fun deleteMissing(ids: List<String>)
}
