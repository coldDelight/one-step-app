package com.colddelight.data.mapper

import com.colddelight.database.model.ExerciseEntity
import com.colddelight.model.ExerciseInfo

object ExerciseEntityMapper : EntityMapper<ExerciseInfo, ExerciseEntity> {


    override fun asEntity(domain: ExerciseInfo): ExerciseEntity {
        return ExerciseEntity(
            id = domain.id,
            name = domain.name,
            category = domain.category,
        )
    }
    fun asEntity(domain: List<ExerciseInfo>): List<ExerciseEntity> {
        return domain.map {
            it.asEntity()
        }
    }


    override fun asDomain(entity: ExerciseEntity): ExerciseInfo {
        return ExerciseInfo(
            id = entity.id,
            name = entity.name,
            category = entity.category,
        )
    }
    fun asDomain(entity: List<ExerciseEntity>): List<ExerciseInfo> {
        return entity.map { it.asDomain() }
    }




}

fun ExerciseInfo.asEntity(): ExerciseEntity {
    return ExerciseEntityMapper.asEntity(this)
}

fun ExerciseEntity.asDomain(): ExerciseInfo {
    return ExerciseEntityMapper.asDomain(this)
}

fun List<ExerciseInfo>.asEntity(): List<ExerciseEntity> {
    return ExerciseEntityMapper.asEntity(this)
}


fun List<ExerciseEntity>.asDomain(): List<ExerciseInfo> {
    return ExerciseEntityMapper.asDomain(this)
}
