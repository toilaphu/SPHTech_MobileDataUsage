package com.sphtech.mobiledatausage

import android.app.Application
import com.sphtech.mobiledatausage.injector.ContextModule
import com.sphtech.mobiledatausage.injector.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MainApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Any>

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder().contextModule(ContextModule(this)).build().inject(this)
    }

    override fun androidInjector(): AndroidInjector<Any> = androidInjector
}