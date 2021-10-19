package com.example.chatappdemo


import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers


class ChatApplication : Application() {



    private var mInstance: ChatApplication? = null
    private var sharedPreferences: SharedPreferences? = null
    private var scheduler: Scheduler? = null


    companion object {


        var mInstance: ChatApplication? = null

        private var sharedPreferences: SharedPreferences? = null


        fun setmInstance(mInstance: ChatApplication) {
            this.mInstance = mInstance
        }


    }


    override fun onCreate() {
        super.onCreate()

        mInstance = this
        sharedPreferences = getSharedPreferences(getString(R.string.app_name), Context.MODE_PRIVATE)
    }


    fun getSharedPreferences(): SharedPreferences {
        return this.sharedPreferences!!
    }

    fun setmInstance(mInstance: ChatApplication) {
        this.mInstance = mInstance
    }

    fun savePreferenceDataString(
        key: String,
        value: /*private var mInstance: AppClass? = null*/String
    ) {
        val editor = sharedPreferences!!.edit()
        editor.putString(key, value)
        editor.commit()
    }

    fun savePreferenceDataBoolean(key: String, value: Boolean?) {
        val editor = sharedPreferences!!.edit()
        editor.putBoolean(key, value!!)
        editor.commit()
    }

    fun clearPreference() {
        val editor = sharedPreferences!!.edit()
        editor.clear()
        editor.commit()
    }



    fun subscribeScheduler(): Scheduler? {
        if (scheduler == null) {
            scheduler = Schedulers.io()
        }

        return this.scheduler
    }

}