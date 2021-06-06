package ru.tinkoff.domain.gif

import io.reactivex.rxjava3.core.Observable
import ru.tinkoff.data.model.Gif
import ru.tinkoff.data.repository.developerslife.DevelopersLifeRepository
import ru.tinkoff.domain.common.ObservableUseCase

class GetGifsUseCase(
	private val repository: DevelopersLifeRepository
) : ObservableUseCase<List<Gif>, Pair<String, Int>>() {

	override fun buildObservableUseCase(params: Pair<String, Int>): Observable<List<Gif>> =
		repository.getGifs(params.first, params.second)
}