package com.randomuser.di

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import timber.log.Timber

const val RANDOM_USER_GENERATOR_BASE_API_PATH = "https://randomuser.me/"

val apiModule = module {
    single<Retrofit> { provideRetrofit() }
}

private fun provideRetrofit(): Retrofit {
    val interceptor = HttpLoggingInterceptor { message ->
        Timber.tag("OkHttp").d(message)
    }.apply {
        setLevel(HttpLoggingInterceptor.Level.BASIC)
    }
    val client: OkHttpClient = OkHttpClient.Builder().addInterceptor(interceptor).build()

    return Retrofit.Builder()
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(buildMoshi()))
            .baseUrl(RANDOM_USER_GENERATOR_BASE_API_PATH)
            .build()
}

fun buildMoshi(): Moshi {
    return Moshi.Builder()
            .addLast(KotlinJsonAdapterFactory())
            .build()
}
