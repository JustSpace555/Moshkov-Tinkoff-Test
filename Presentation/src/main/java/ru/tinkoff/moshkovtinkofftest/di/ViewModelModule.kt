package ru.tinkoff.moshkovtinkofftest.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.tinkoff.moshkovtinkofftest.ui.main.PageViewModel

val viewModelModule = module {

	viewModel { PageViewModel(get()) }
}