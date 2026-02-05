package ru.gureva.ebookreader.feature.bookupload.mapper

import ru.gureva.ebookreader.database.entity.BookEntity
import ru.gureva.ebookreader.feature.bookupload.model.BookMetadata

fun BookMetadata.mapToBookEntity(): BookEntity =
    BookEntity(
        fileName = fileName,
        author = author,
        title = title,
        creationDate = creationDate,
        isLocal = true
    )
