package ru.tinkoff.data.network

import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import ru.tinkoff.data.entity.developerslife.DevelopersLifeAnswerEntity

val API_PATHS = listOf("latest", "hot", "top")

internal interface DevelopersLifeApi {

	@GET("{type}/{page}?json=true")
	fun getFilesList(
		@Path("type")
		type: String,
		@Path("page")
		page: Int
	): Observable<DevelopersLifeAnswerEntity>
}