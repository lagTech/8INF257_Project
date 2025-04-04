package com.example.android_routine.utils

import android.content.Context
import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.android_routine.worker.ReminderWorker
import java.util.Calendar
import java.util.concurrent.TimeUnit
import androidx.work.Data
import java.text.SimpleDateFormat
import java.util.Locale


class  RoutinesUtils {

    fun scheduleDailyExactReminder(context: Context, taskId: Int, title: String, dueTime: String) {
        val formatter = SimpleDateFormat("HH:mm", Locale.getDefault())
        val parsedTime = formatter.parse(dueTime) ?: return

        val now = Calendar.getInstance()

        val scheduledTime = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, parsedTime.hours)
            set(Calendar.MINUTE, parsedTime.minutes)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)

            // If the time has already passed today, schedule for tomorrow
            if (before(now)) {
                add(Calendar.DATE, 1)
            }
        }

        val delay = scheduledTime.timeInMillis - now.timeInMillis

        val data = Data.Builder()
            .putString("title", title)
            .putString("dueTime", dueTime)
            .putString("periodicity", "Daily")
            .build()

        val request = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(delay, TimeUnit.MILLISECONDS)
            .setInputData(data)
            .addTag("daily_reminder_$taskId")
            .build()

        WorkManager.getInstance(context).enqueue(request)

        Log.d("RoutinesUtils", "✅ Scheduled daily task \"$title\" at $dueTime (delay: $delay ms)")
    }

    fun scheduleRecurringReminder(context: Context, taskId: Int, title: String, intervalDays: Long) {
        val data = androidx.work.Data.Builder()
            .putString("title", title)
            .build()

        val request = androidx.work.PeriodicWorkRequestBuilder<ReminderWorker>(
            intervalDays, java.util.concurrent.TimeUnit.DAYS
        )
            .setInputData(data)
            .addTag("recurring_reminder_$taskId")
            .build()

        androidx.work.WorkManager.getInstance(context).enqueueUniquePeriodicWork(
            "recurring_reminder_$taskId",
            androidx.work.ExistingPeriodicWorkPolicy.UPDATE,
            request
        )
    }

    fun scheduleTaskReminder(context: Context, taskId: Int, title: String, dueDate: String, dueTime: String) {
        val formatter = java.text.SimpleDateFormat("yyyy-MM-dd HH:mm", java.util.Locale.getDefault())
        val dueMillis = try {
            formatter.parse("$dueDate $dueTime")?.time ?: return
        } catch (e: Exception) {
            e.printStackTrace()
            return
        }

        val now = System.currentTimeMillis()
        val delay = dueMillis - now
        if (delay <= 0) return // Already past due

        val data = androidx.work.Data.Builder()
            .putString("title", title)
            .build()

        val request = androidx.work.OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(delay, java.util.concurrent.TimeUnit.MILLISECONDS)
            .setInputData(data)
            .addTag("task_reminder_$taskId")
            .build()

       Log.d("ReminderScheduler", "Scheduling: $title at $dueDate $dueTime")
       Log.d("ReminderScheduler", "Due millis = $dueMillis, Now = $now, Delay = $delay ms")

       androidx.work.WorkManager.getInstance(context).enqueue(request)
    }


}