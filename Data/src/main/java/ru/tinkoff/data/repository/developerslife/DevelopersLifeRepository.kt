package ru.tinkoff.data.repository.developerslife

import io.reactivex.rxjava3.core.Observable
import ru.tinkoff.data.model.Gif
import ru.tinkoff.data.repository.common.BaseRepository

interface DevelopersLifeRepository : BaseRepository {

	fun getGifs(path: String, page: Int): Observable<List<Gif>>
}