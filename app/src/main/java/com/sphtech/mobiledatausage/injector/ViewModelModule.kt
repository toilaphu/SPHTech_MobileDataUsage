package com.sphtech.mobiledatausage.injector

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.sphtech.mobiledatausage.viewmodels.MobileDataUsageViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {
    //The default factory only works with default constructor
    @Binds
    @IntoMap
    @ViewModelKey(MobileDataUsageViewModel::class)
    abstract fun bindMobileDataUsageViewModel(mobileDataUsageViewModel: MobileDataUsageViewModel): ViewModel

    @Binds
    abstract fun bindDaggerViewModelFactory(factory: DaggerViewModelFactory): ViewModelProvider.Factory
}