package com.oznurdemir.kotlin_workmanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.work.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val number = Data.Builder().putInt("myInt",1).build()// Göndereceğimiz değeri inşa ediyoruz.

        val constrains = Constraints.Builder().setRequiresCharging(false).build()

        /*
        val myWorkRequest : WorkRequest = OneTimeWorkRequestBuilder<RefreshedData>()
            //.addTag("myTag")
            .setConstraints(constrains)
            //.setInitialDelay(5,TimeUnit.HOURS)
            .setInputData(number)
            .build()


        // WorkManager'ı çalıştırıyoruz.
        WorkManager.getInstance(this).enqueue(myWorkRequest)

         */

        // Periyodik work rwquest'i oluşturduk ve oluştururken en az 15 dakika olması gerekmektedir.
        val myWorkRequest2 : WorkRequest = PeriodicWorkRequestBuilder<RefreshedData>(15,TimeUnit.MINUTES)
            .setConstraints(constrains)
            .setInputData(number)
            .build()
        WorkManager.getInstance(this).enqueue(myWorkRequest2)

        WorkManager.getInstance(this).getWorkInfoByIdLiveData(myWorkRequest2.id).observe(this,
        Observer {
            if(it.state == WorkInfo.State.RUNNING){
                println("running")
            }else if(it.state == WorkInfo.State.FAILED){
                println("failed")
            }else if(it.state == WorkInfo.State.SUCCEEDED){
                println("succeeded")
            }
        })

        // Bütün workleri iptal etmek
        //WorkManager.getInstance(this).cancelAllWork()


        /*
        //Chaining
        val oneTimeWorkRequest : OneTimeWorkRequest = OneTimeWorkRequestBuilder<RefreshedData>()
            .setConstraints(constrains)
            .setInputData(number)
            .build()

        WorkManager.getInstance(this).beginWith(oneTimeWorkRequest).then(oneTimeWorkRequest).enqueue()

         */
    }
}