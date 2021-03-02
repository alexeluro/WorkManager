package com.inspiredcoda.workmanager

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class CustomWorker(
        context: Context,
        workerParams: WorkerParameters
): Worker(context, workerParams) {

    private val TAG = "CustomWorker"

    override fun doWork(): Result {

        CoroutineScope(Dispatchers.Main).launch {
            for (x in 10 downTo 0) {
                Log.d(TAG, "doWork: TIME BOMB: $x")
                delay(1000)
            }
        }

        displayNotification("Custom Worker", "Background Task Completed")

        return Result.success()
    }

    private fun displayNotification(task: String, description: String){

        val manager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel("inspiredCoda_1", "inspiredCoda", NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }

        val builder = NotificationCompat.Builder(applicationContext, "inspiredCoda_1")
                .setContentTitle(task)
                .setContentText(description)
                .setSmallIcon(R.mipmap.ic_launcher_round)

        manager.notify(1, builder.build())
    }



}