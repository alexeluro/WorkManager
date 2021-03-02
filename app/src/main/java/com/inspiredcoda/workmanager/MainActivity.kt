package com.inspiredcoda.workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.work.*
import com.inspiredcoda.workmanager.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val root = binding.root
        setContentView(root)

        val constraints = Constraints.Builder()
                .setRequiresCharging(true)
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

        val workRequest = OneTimeWorkRequest.Builder(CustomWorker::class.java)
                .setConstraints(constraints)
                .build()

        binding.doWorkBtn.setOnClickListener {
            WorkManager.getInstance(applicationContext).enqueue(workRequest)
        }

        WorkManager.getInstance(applicationContext).getWorkInfoByIdLiveData(workRequest.id).observe(this){workInfo ->
            binding.doWorkState.text = workInfo.state.name
        }

    }







}