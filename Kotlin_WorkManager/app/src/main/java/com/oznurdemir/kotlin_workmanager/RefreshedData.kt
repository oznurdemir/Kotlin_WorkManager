package com.oznurdemir.kotlin_workmanager

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters

class RefreshedData(val context: Context, workerParams: WorkerParameters) :
    Worker(context, workerParams) {
    override fun doWork(): Result {
        val getData = inputData
        val myNumber = getData.getInt("myInt",0)
        refreshDatabase(myNumber)
        return Result.success()
    }
    private fun refreshDatabase(number : Int){
        val sharedPrefence = context.getSharedPreferences("com.oznurdemir.kotlin_workmanager",Context.MODE_PRIVATE)
        var mySavedNumber = sharedPrefence.getInt("myNumber",0)
        mySavedNumber = mySavedNumber + number
        println(mySavedNumber)
        sharedPrefence.edit().putInt("myNumber",mySavedNumber).apply()//Change value of mySavedNumber
    }
}