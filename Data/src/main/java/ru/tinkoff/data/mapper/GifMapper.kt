package ru.tinkoff.data.mapper

import ru.tinkoff.data.entity.developerslife.GifEntity
import ru.tinkoff.data.mapper.common.BaseMapper
import ru.tinkoff.data.model.Gif

internal class GifMapper : BaseMapper<GifEntity, Gif> {

	override fun entityToModel(entity: GifEntity): Gif = Gif(
		id = entity.id,
		gifUrl = entity.gifURL,
		description = entity.description
	)

}