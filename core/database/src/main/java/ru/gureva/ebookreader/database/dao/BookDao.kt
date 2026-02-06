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

    @Query("SELECT * FROM books ORDER BY creation_date DESC")
    fun getAll(): Flow<List<BookEntity>>

    @Query("SELECT * FROM books")
    suspend fun getAllOnce(): List<BookEntity>

    @Upsert
    suspend fun upsertAll(books: List<BookEntity>)

    @Query("DELETE FROM books WHERE fileName NOT IN (:ids)")
    suspend fun deleteMissing(ids: List<String>)

    @Query("UPDATE books SET is_local = :isLocal WHERE fileName = :fileName")
    suspend fun updateIsLocalByFileName(fileName: String, isLocal: Boolean)
}
