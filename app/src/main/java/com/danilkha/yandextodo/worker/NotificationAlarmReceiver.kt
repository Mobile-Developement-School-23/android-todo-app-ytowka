package com.danilkha.yandextodo.worker

import android.app.AlarmManager
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.TaskStackBuilder
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.danilkha.yandextodo.R
import com.danilkha.yandextodo.ui.MainActivity
import com.danilkha.yandextodo.ui.models.TodoItem
import java.util.Date
import javax.inject.Inject

class NotificationAlarmReceiver @Inject constructor() : BroadcastReceiver() {

    @Inject
    lateinit var prefs: SharedPreferences

    companion object {
        const val TYPE_ONE_TIME = "OneTi meAlarm"
        const val EXTRA_MESSAGE = "message"
        const val EXTRA_TYPE = "type"
        const val EXTRA_ID = "id"
        private const val ID_ONETIME = 1
        private const val ID_REPEATING = 2
    }

    override fun onReceive(context: Context, intent: Intent) {
        val type = intent.getStringExtra(EXTRA_TYPE)
        val message = intent.getStringExtra(EXTRA_MESSAGE)
        val id = intent.getStringExtra(EXTRA_ID)

        val title = type
        val notifId =
            if (type.equals(TYPE_ONE_TIME, ignoreCase = true)) ID_ONETIME else ID_REPEATING

        title?.let {
            showAlarmNotification(context, it, message, notifId, id!!)
        }

    }



    fun setOneTimeAlarm(
        context: Context,
        date: Date?,
        imp: String,
        text: String,
        id: String,
        hash:Int
    ) {

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationAlarmReceiver::class.java)
        intent.putExtra(EXTRA_MESSAGE, imp)
        intent.putExtra(EXTRA_TYPE, text)
        intent.putExtra(EXTRA_ID, id)

        if (date == null) {
            val search = prefs.getInt(id, 0)
            if (search != 0) {
                val editor = prefs.edit()
                editor.remove(id)
                editor.apply()
                cancelAlarm(context, search)
            }
        } else if (date.time > Date().time) {

            val search = prefs.getInt(id, 0)
            if (search ==  0) {
                val editor = prefs.edit()
                editor.putInt(id, hash)
                editor.apply()
                val pendingIntent = PendingIntent.getBroadcast(
                    context,
                    hash,
                    intent,
                    PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                )
                alarmManager.set(AlarmManager.RTC_WAKEUP, date.time, pendingIntent)
            } else {
                if (search != hash) {
                    cancelAlarm(context, hash)
                    val editor = prefs.edit()
                    editor.putInt(id, hash)
                    editor.apply()
                    val pendingIntent = PendingIntent.getBroadcast(
                        context,
                        hash,
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
                    )
                    alarmManager.set(AlarmManager.RTC_WAKEUP, date.time, pendingIntent)
                }
            }
        }
    }

    fun cancelAlarm(context: Context, type: Int) {
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, NotificationAlarmReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            type,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        pendingIntent.cancel()

        alarmManager.cancel(pendingIntent)
    }

    private fun createNavigationIntent(context: Context, id: String): PendingIntent {
        val intent = Intent(context, MainActivity::class.java)
        intent.putExtra(MainActivity.TASK_ID, id)
        val pendingIntent = TaskStackBuilder.create(context).run {
            addNextIntent(intent)
            getPendingIntent(0,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        }
        return pendingIntent
    }

    private fun showAlarmNotification(
        context: Context,
        title: String,
        message: String?,
        notifId: Int,
        id: String
    ) {
        val CHANNEL_ID = "Channel_1"
        val CHANNEL_NAME = "deadline notifications"

        val pIntent = createNavigationIntent(context, id)

        val notificationManagerCompat =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_delete)
            .setContentTitle(title)
            .setContentText(message)
            .setColor(ContextCompat.getColor(context, android.R.color.transparent))
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setAutoCancel(true)
            .setContentIntent(pIntent)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setForegroundServiceBehavior(NotificationCompat.FOREGROUND_SERVICE_IMMEDIATE)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                CHANNEL_ID,
                CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            )

            channel.lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            builder.setChannelId(CHANNEL_ID)
            notificationManagerCompat.createNotificationChannel(channel)
        }

        val notification = builder.build()

        notificationManagerCompat.notify(notifId, notification)
    }

    fun setNotifications(context: Context, toDoItems: List<TodoItem>) {
        toDoItems.map {
            setOneTimeAlarm(
                context,
                it.time,
                it.importance.name,
                it.text,
                it.id,
                it.hashCode()
            )
        }
    }
}