package ru.tinkoff.data.di

import org.koin.dsl.module
import ru.tinkoff.data.repository.developerslife.DevelopersLifeRepository
import ru.tinkoff.data.repository.developerslife.DevelopersLifeRepositoryImpl

internal val repositoryModule = module {

	single<DevelopersLifeRepository> { DevelopersLifeRepositoryImpl(get(), get()) }
}