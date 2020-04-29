package com.sphtech.mobiledatausage.injector

import android.app.Application
import androidx.room.Room
import com.sphtech.mobiledatausage.data.AppDatabase
import com.sphtech.mobiledatausage.utilities.DATABASE_NAME
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DBModule {
    @Provides
    @Singleton
    fun provideAppDatabase(application: Application): AppDatabase {
        return Room.databaseBuilder(application, AppDatabase::class.java, DATABASE_NAME).build()
    }
}