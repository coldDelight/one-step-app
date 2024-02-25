package com.colddelight.data.mapper

import com.colddelight.database.model.RoutineDayEntity
import com.colddelight.model.RoutineDay

//TODO 기본이 List가 아니게
object RoutineDayEntityMapper : EntityMapper<List<RoutineDay>, List<RoutineDayEntity>> {

    fun asEntity(domain: RoutineDay): RoutineDayEntity {
        return RoutineDayEntity(
            routineId = domain.routineId,
            dayOfWeek = domain.dayOfWeek,
            categoryList = domain.categoryList,
            id = domain.id,
        )
    }

    override fun asEntity(domain: List<RoutineDay>): List<RoutineDayEntity> {
        return domain.map { routineDay ->
            routineDay.asEntity()
        }
    }

    fun asDomain(entity: RoutineDayEntity): RoutineDay {
        return RoutineDay(
            routineId = entity.routineId,
            dayOfWeek = entity.dayOfWeek,
            categoryList = entity.categoryList,
            id = entity.id,
        )
    }

    override fun asDomain(entity: List<RoutineDayEntity>): List<RoutineDay> {
        return entity.map { routineDayEntity ->
            routineDayEntity.asDomain()
        }
    }
}

fun List<RoutineDay>.asEntity(): List<RoutineDayEntity> {
    return RoutineDayEntityMapper.asEntity(this)
}

fun RoutineDay.asEntity(): RoutineDayEntity {
    return RoutineDayEntityMapper.asEntity(this)
}

fun List<RoutineDayEntity>.asDomain(): List<RoutineDay> {
    return RoutineDayEntityMapper.asDomain(this)
}

fun RoutineDayEntity.asDomain(): RoutineDay {
    return RoutineDayEntityMapper.asDomain(this)
}

