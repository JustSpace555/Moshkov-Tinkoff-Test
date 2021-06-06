package ru.tinkoff.domain.di

import org.koin.dsl.module
import ru.tinkoff.domain.gif.GetGifsUseCase

internal val useCaseModule = module {

	factory { GetGifsUseCase(get()) }
}