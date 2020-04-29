package com.sphtech.mobiledatausage.injector

import com.sphtech.mobiledatausage.ui.DataUsageFragment
import com.sphtech.mobiledatausage.ui.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module(includes = [ContextModule::class])
abstract class AppModule {

    @ContributesAndroidInjector
    abstract fun mainActivityInjector(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeDataUsageFragment(): DataUsageFragment
}