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
import ru.gureva.ebookreader.core.util.ResourceManager
import ru.gureva.ebookreader.feature.bookupload.R
import ru.gureva.ebookreader.feature.bookupload.constants.BookUploadWorkerParams
import ru.gureva.ebookreader.feature.bookupload.model.UploadBookRequest
import ru.gureva.ebookreader.feature.bookupload.usecase.UploadBookUseCase

class BookUploadWorker(
    context: Context,
    workerParams: WorkerParameters,
    private val uploadBookUseCase: UploadBookUseCase,
    private val resourceManager: ResourceManager
) : CoroutineWorker(context, workerParams) {

    override suspend fun doWork(): Result {
        setForeground(getForegroundInfo())

        val filePath = inputData.getString(BookUploadWorkerParams.FILE_PATH) ?: return Result.failure()
        val title = inputData.getString(BookUploadWorkerParams.TITLE) ?: return Result.failure()
        val author = inputData.getString(BookUploadWorkerParams.AUTHOR) ?: return Result.failure()
        val userId = inputData.getString(BookUploadWorkerParams.USER_ID) ?: return Result.failure()

        val metadata = UploadBookRequest(
            filePath = filePath,
            title = title,
            author = author,
            userId = userId
        )

        return try {
            uploadBookUseCase(metadata)
            showSuccessNotification(title, filePath)
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
                NotificationManager.IMPORTANCE_DEFAULT,
            )

            val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        createNotificationChannel()
        val notification = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(resourceManager.getString(R.string.book_uploading_notification))
            .setSmallIcon(R.drawable.upload)
            .setOngoing(true)
            .build()

        return ForegroundInfo(NOTIFICATION_ID, notification)
    }

    private fun showSuccessNotification(bookTitle: String, filePath: String) {
        val notificationManager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notification = NotificationCompat.Builder(applicationContext, NOTIFICATION_CHANNEL_ID)
            .setContentTitle(resourceManager.getString(R.string.book_downloaded_successfully))
            .setContentText(resourceManager.getString(R.string.book_uploading_successfully_notification, bookTitle))
            .setSmallIcon(R.drawable.upload)
            .setAutoCancel(true)
            .build()

        notificationManager.notify(filePath.hashCode(), notification)
    }

    companion object {
        private const val NOTIFICATION_CHANNEL_ID = "book_upload_channel"
        private const val NOTIFICATION_CHANNEL_NAME = "Book Upload Channel"
        private const val NOTIFICATION_ID = 1001
    }
}

