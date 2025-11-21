package ru.gureva.ebookreader.feature.bookupload.model

data class BookMetadata(
    val data: ByteArray,
    val fileName: String,
    val title: String,
    val author: String,
    val userId: String
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as BookMetadata

        if (!data.contentEquals(other.data)) return false
        if (fileName != other.fileName) return false
        if (title != other.title) return false
        if (author != other.author) return false
        if (userId != other.userId) return false

        return true
    }

    override fun hashCode(): Int {
        var result = data.contentHashCode()
        result = 31 * result + fileName.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + author.hashCode()
        result = 31 * result + userId.hashCode()
        return result
    }
}
