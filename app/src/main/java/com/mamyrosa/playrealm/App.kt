package com.mamyrosa.playrealm

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import com.mamyrosa.playrealm.utilities.Constants
import io.realm.Realm
import io.realm.RealmConfiguration

class App : Application() {
    companion object {
        lateinit var instance: App
            private set
        lateinit var preference: SharedPreferences
            private set

        lateinit var res: Resources
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        res = resources
        initializePreference()
        initializeRealmDatabase()
    }

    private fun initializePreference() {
        preference = getSharedPreferences(Constants.Prefs.filename, Context.MODE_PRIVATE)
    }

    //initializing database
    private fun initializeRealmDatabase() {
        Realm.init(instance)
        val realmConfiguration = RealmConfiguration.Builder()
            .schemaVersion(4)
            .deleteRealmIfMigrationNeeded()
            .allowQueriesOnUiThread(true)
            .allowWritesOnUiThread(true)
            .build()
        Realm.setDefaultConfiguration(realmConfiguration)
    }

}