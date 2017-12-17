package com.lucasbais.paymentdemo.internal.di.module


import android.content.Context
import android.content.SharedPreferences
import com.google.gson.FieldNamingPolicy
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.lucasbais.paymentdemo.PaymentService
import com.lucasbais.paymentdemo.datasource.PaymentDataService
import com.lucasbais.paymentdemo.datasource.database.AppDatabase
import com.lucasbais.paymentdemo.datasource.network.CamelCaseNamingPolicy
import com.lucasbais.paymentdemo.datasource.network.PaymentClient
import com.lucasbais.paymentdemo.datasource.network.PublicKeyInterceptor
import com.lucasbais.paymentdemo.internal.excecution.MainThreadExecutor
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.Executor
import java.util.concurrent.Executors
import javax.inject.Named
import javax.inject.Singleton


@Module
class ApplicationModule(private val context: Context) {

    @Provides
    @Singleton
    internal fun database(context: Context): AppDatabase {
        return AppDatabase.create(context)
    }

    @Provides
    @Singleton
    internal fun providesApplicationContext(): Context {
        return context
    }

    @Provides
    @Singleton
    @Named("mainExecutor")
    internal fun mainExecutor(): Executor {
        return MainThreadExecutor()
    }

    @Provides
    @Singleton
    @Named("netExecutor")
    internal fun netExecutor(): Executor {
        return Executors.newFixedThreadPool(3)
    }

    @Provides
    @Singleton
    @Named("diskExecutor")
    internal fun diskExecutor(): Executor {
        return Executors.newSingleThreadExecutor()
    }

    @Provides
    @Singleton
    internal fun sharedPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(context.packageName, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    internal fun okHttpClient(context: Context): OkHttpClient {
        val okHttpClient = OkHttpClient.Builder()
        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okHttpClient
                .addInterceptor(PublicKeyInterceptor("444a9ef5-8a6b-429f-abdf-587639155d88"))
                .addInterceptor(httpLoggingInterceptor)
                .cache(Cache(File(context.cacheDir, "responses"), 10 * 1024 * 1024))
        return okHttpClient.build()
    }

    @Provides
    @Singleton
    internal fun gson(): Gson {
        return GsonBuilder()
                .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .setFieldNamingStrategy(CamelCaseNamingPolicy())
                .create()
    }

    @Provides
    @Singleton
    internal fun retrofitClient(client: OkHttpClient, gson: Gson): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .baseUrl("https://api.mercadopago.com/")
                .build()
    }

    @Provides
    @Singleton
    internal fun paymentClient(retrofit: Retrofit): PaymentClient {
        return retrofit.create(PaymentClient::class.java)
    }

    @Provides
    @Singleton
    internal fun paymentService(paymentDataService: PaymentDataService): PaymentService {
        return paymentDataService
    }
}