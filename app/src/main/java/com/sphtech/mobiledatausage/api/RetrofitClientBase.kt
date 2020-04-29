package com.sphtech.mobiledatausage.api

import android.util.Log
import com.sphtech.mobiledatausage.utilities.BASE_URL
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class RetrofitClientBase {

    private var retrofit: Retrofit.Builder? = null

    @Provides
    @Singleton
    fun provideMobileDataUsageService(builder: Retrofit.Builder): MobileDataUsageApi {
        return builder
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(MobileDataUsageApi::class.java)
    }

    @Provides
    @Singleton
    fun provideRetrofitBuilder(okHttpClient: OkHttpClient): Retrofit.Builder {
        return retrofit ?: synchronized(this) {
            retrofit ?: run {
                return@run Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())

            }.also { retrofit = it }
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val logger = HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
            Log.d("RetrofitClientBase", "RequestData: $it")
        })
        logger.level = HttpLoggingInterceptor.Level.BASIC
        return OkHttpClient.Builder()
            .addInterceptor(logger)
            .build()
    }
}