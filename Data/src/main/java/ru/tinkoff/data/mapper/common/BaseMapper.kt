package ru.tinkoff.data.mapper.common

import ru.tinkoff.data.entity.common.BaseEntity
import ru.tinkoff.data.model.common.BaseModel

internal interface BaseMapper <in E : BaseEntity, out M : BaseModel> {

	fun entityToModel(entity: E): M

	fun entityListToModelList(list: List<E>): List<M> = list.map { entityToModel(it) }
}