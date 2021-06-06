package ru.tinkoff.moshkovtinkofftest.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.tinkoff.data.model.Gif
import ru.tinkoff.domain.gif.GetGifsUseCase

class PageViewModel(private val gifsUseCase: GetGifsUseCase) : ViewModel() {

    private lateinit var path: String

	private val _gifsList = MutableLiveData<List<Gif>>()
	val gifsList = _gifsList

	var gifsPage = 0
	var gifNumber = 0
	val gifs = mutableListOf<Gif>()

    fun setPath(str: String) { path = str }

	fun requestGifs(onError: (t: Throwable) -> Unit) = gifsUseCase.execute(
		onNext = { _gifsList.postValue(it) },
		onError = onError,
		params = Pair(path, gifsPage)
	)
}