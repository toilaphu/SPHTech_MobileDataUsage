package com.sphtech.mobiledatausage.injector

import android.app.Application
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ContextModule constructor(private val application: Application) {

    @Provides
    @Singleton
    fun provideApplication() = application

}