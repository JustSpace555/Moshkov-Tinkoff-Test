package ru.tinkoff.data.entity.developerslife

import ru.tinkoff.data.entity.common.BaseEntity

internal data class GifEntity(
	val id: Long,
	val description: String,
	val gifURL: String
) : BaseEntity