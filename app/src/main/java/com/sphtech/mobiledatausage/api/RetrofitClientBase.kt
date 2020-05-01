package com.sphtech.mobiledatausage.api

import android.app.Application
import android.util.Log
import com.sphtech.mobiledatausage.utilities.AppUtils
import com.sphtech.mobiledatausage.utilities.BASE_URL
import dagger.Module
import dagger.Provides
import okhttp3.Cache
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
    fun provideOkHttpClient(application: Application): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor(HttpLoggingInterceptor.Logger {
                Log.d("RetrofitClientBase", it)
            }).apply {
                level = HttpLoggingInterceptor.Level.BASIC
            })
            .addNetworkInterceptor { chain ->
                val response = chain.proceed(chain.request())
                response.newBuilder()
                    .header(
                        "Cache-Control", "public, max-age=" + 60
                    ) // Get from Cache for 1 minute
                    .removeHeader("Pragma")
                    .build()
            }
            .addInterceptor { chain ->
                var request = chain.request()
                if (!AppUtils.isNetworkAvailable(application)) {
                    request = request.newBuilder()
                        .header(
                            "Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7
                        ) // Stored to 7 days
                        .removeHeader("Pragma") // Effects along the request-response chain.
                        .build()
                }
                chain.proceed(request)
            }
            .cache(Cache(application.cacheDir, (10 * 1024 * 1024)))
            .build()
    }
}