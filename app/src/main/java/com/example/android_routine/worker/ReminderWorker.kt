package com.example.android_routine.worker

import android.content.Context
import androidx.work.*
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.android_routine.R
import java.util.Calendar
import java.util.concurrent.TimeUnit

class ReminderWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(context, workerParams) {

    override fun doWork(): Result {
        val title = inputData.getString("title") ?: "Task Reminder"
        val periodicity = inputData.getString("periodicity")
        val dueTime = inputData.getString("dueTime")

        showNotification("Task Reminder", "It's time for: $title")
        Log.d("ReminderWorker", "🚨 Reminder triggered for: $title")

        if (periodicity == "Daily" && dueTime != null) {
            scheduleNextDailyReminder(applicationContext, title, dueTime)
        }

        return Result.success()
    }

    private fun showNotification(title: String, message: String) {
        val channelId = "routine_channel"
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Routine Notifications",
                NotificationManager.IMPORTANCE_DEFAULT
            )
            notificationManager.createNotificationChannel(channel)
        }

        val notification = NotificationCompat.Builder(applicationContext, channelId)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(android.R.drawable.ic_dialog_info)
            .build()

        notificationManager.notify(System.currentTimeMillis().toInt(), notification)
    }

    private fun scheduleNextDailyReminder(context: Context, title: String, dueTime: String) {
        val formatter = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
        var time = formatter.parse(dueTime) ?: return

        val calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            val timeOnly = Calendar.getInstance().apply { time = time }
            set(Calendar.HOUR_OF_DAY, timeOnly.get(Calendar.HOUR_OF_DAY))
            set(Calendar.MINUTE, timeOnly.get(Calendar.MINUTE))
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            add(Calendar.DATE, 1)
        }

        val delay = calendar.timeInMillis - System.currentTimeMillis()

        val data = Data.Builder()
            .putString("title", title)
            .putString("dueTime", dueTime)
            .putString("periodicity", "Daily")
            .build()

        val nextWork = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(data)
            .addTag("daily_reminder_$title")
            .build()

        WorkManager.getInstance(context).enqueue(nextWork)

        Log.d("ReminderWorker", "✅ Rescheduled next daily for $title at $dueTime tomorrow")
    }
}
