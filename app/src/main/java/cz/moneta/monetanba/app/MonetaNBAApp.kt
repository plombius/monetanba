package cz.moneta.monetanba.app

import android.app.Application
import cz.moneta.monetanba.common.di.AppModule

class MonetaNBAApp: Application() {

    companion object{
        lateinit var appModule: AppModule
    }

    override fun onCreate() {
        super.onCreate()
        appModule = AppModule(this)
    }
}