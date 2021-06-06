package ru.tinkoff.data.di

import org.koin.dsl.module
import ru.tinkoff.data.mapper.GifMapper

internal val mapperModule = module {
	factory { GifMapper() }
}