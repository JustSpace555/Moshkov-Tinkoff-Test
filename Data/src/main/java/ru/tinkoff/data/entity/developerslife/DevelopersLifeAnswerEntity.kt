package ru.tinkoff.data.entity.developerslife

import ru.tinkoff.data.entity.common.BaseEntity

internal data class DevelopersLifeAnswerEntity(
	val result: List<GifEntity>
) : BaseEntity