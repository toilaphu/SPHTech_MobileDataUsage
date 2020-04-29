package com.sphtech.mobiledatausage.injector

import com.sphtech.mobiledatausage.MainApplication
import com.sphtech.mobiledatausage.api.RetrofitClientBase
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        AppModule::class,
        DBModule::class,
        RetrofitClientBase::class,
        ContextModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent : AndroidInjector<MainApplication> {
}