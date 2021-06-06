package ru.tinkoff.data.di

import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import ru.tinkoff.data.network.DevelopersLifeApi

private const val BASE_URL = "https://developerslife.ru/"

internal val networkModule = module {

	single {
		Retrofit.Builder()
			.baseUrl(BASE_URL)
			.addConverterFactory(GsonConverterFactory.create())
			.addCallAdapterFactory(RxJava3CallAdapterFactory.create())
			.build()
	}

	single { get<Retrofit>().create(DevelopersLifeApi::class.java) }
}