package ru.gureva.ebookreader.feature.bookupload.background

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import kotlinx.io.IOException
import ru.gureva.ebookreader.feature.bookupload.R
import ru.gureva.ebookreader.feature.bookupload.model.BookMetadata
import ru.gureva.ebookreader.feature.bookupload.usecase.UploadBookUseCase

class BookUploadWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val uploadBookUseCase: UploadBookUseCase
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        setForeground(getForegroundInfo())

        val filePath = inputData.getString("filePath") ?: return Result.failure()
        val fileName = inputData.getString("fileName") ?: return Result.failure()
        val title = inputData.getString("title") ?: return Result.failure()
        val author = inputData.getString("author") ?: return Result.failure()
        val userId = inputData.getString("userId") ?: return Result.failure()

        val metadata = BookMetadata(
            filePath = filePath,
            fileName = fileName,
            title = title,
            author = author,
            userId = userId
        )

        return try {
            uploadBookUseCase(metadata)
            Result.success()

        } catch (e: IOException) {
            if (runAttemptCount < 3) {
                Result.retry()
            } else {
                Result.failure()
            }

        } catch (e: Exception) {
            Result.failure()
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                NOTIFICATION_CHANNEL_ID,
                NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH,
            )

            val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        createNotificationChannel()

        val notification = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID)
            .setContentTitle("Uploading book…")
            .setSmallIcon(R.drawable.upload)
            .setOngoing(true)
            .build()

        return ForegroundInfo(NOTIFICATION_ID, notification)
    }

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "book_upload_channel"
        private const val NOTIFICATION_CHANNEL_NAME = "Book Upload Channel"
        private const val NOTIFICATION_ID = 1001
    }
}

