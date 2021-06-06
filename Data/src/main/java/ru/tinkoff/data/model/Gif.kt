package ru.tinkoff.data.model

import ru.tinkoff.data.model.common.BaseModel

data class Gif(
	val id: Long,
	val description: String,
	val gifUrl: String
) : BaseModel