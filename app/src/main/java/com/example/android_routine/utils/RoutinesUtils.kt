package com.example.android_routine.utils

import android.content.Context
import android.util.Log
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.android_routine.worker.ReminderWorker
import java.util.Calendar
import java.util.concurrent.TimeUnit
import androidx.work.Data


class  RoutinesUtils {

    fun scheduleDailyExactReminder(context: Context, taskId: Int, title: String, dueTime: String) {
        val formatter = java.text.SimpleDateFormat("HH:mm", java.util.Locale.getDefault())
        var time = formatter.parse(dueTime) ?: return

        val calendar = Calendar.getInstance().apply {
            val now = System.currentTimeMillis()
            timeInMillis = now
            val timeOnly = Calendar.getInstance().apply { time = time }
            set(Calendar.HOUR_OF_DAY, timeOnly.get(Calendar.HOUR_OF_DAY))
            set(Calendar.MINUTE, timeOnly.get(Calendar.MINUTE))
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
            if (timeInMillis <= now) add(Calendar.DATE, 1) // schedule for tomorrow
        }

        val delay = calendar.timeInMillis - System.currentTimeMillis()

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

        Log.d("RoutinesUtils", "Scheduled daily task \"$title\" at $dueTime (delay: $delay ms)")
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