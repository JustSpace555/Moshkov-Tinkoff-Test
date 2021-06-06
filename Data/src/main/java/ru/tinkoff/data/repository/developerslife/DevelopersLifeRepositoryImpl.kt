package ru.tinkoff.data.repository.developerslife

import io.reactivex.rxjava3.core.Observable
import ru.tinkoff.data.mapper.GifMapper
import ru.tinkoff.data.model.Gif
import ru.tinkoff.data.network.DevelopersLifeApi

internal class DevelopersLifeRepositoryImpl(
	private val api: DevelopersLifeApi,
	private val gifMapper: GifMapper
) : DevelopersLifeRepository {

	override fun getGifs(path: String, page: Int): Observable<List<Gif>> =
		api.getFilesList(path, page).map { gifMapper.entityListToModelList(it.result) }
}