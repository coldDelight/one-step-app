package com.colddelight.data.mapper

import com.colddelight.database.model.RoutineDayEntity
import com.colddelight.database.model.RoutineEntity
import com.colddelight.model.ExerciseCategory
import com.colddelight.model.Routine

object RoutineEntityMapper : EntityMapper<Routine, RoutineEntity> {
    override fun asEntity(domain: Routine): RoutineEntity {
        return RoutineEntity(
            id = domain.id,
            name = domain.name,
            cnt = domain.cnt,
        )
    }

    override fun asDomain(entity: RoutineEntity): Routine {
        return Routine(
            id = entity.id,
            name = entity.name,
            cnt = entity.cnt,
        )
    }

    fun asDomain(entity: Map<RoutineEntity, RoutineDayEntity>): Routine {
        val routine = entity.keys.first()
        val categoryList = entity.values.first().categoryList
        return Routine(
            id = routine.id,
            name = routine.name,
            cnt = routine.cnt,
            categoryIdList = categoryList.map {
                ExerciseCategory.fromId(it) ?: ExerciseCategory.CHEST
            }
        )
    }

}

fun Routine.asEntity(): RoutineEntity {
    return RoutineEntityMapper.asEntity(this)
}

fun RoutineEntity.asDomain(): Routine {
    return RoutineEntityMapper.asDomain(this)
}

fun Map<RoutineEntity, RoutineDayEntity>.asDomain(): Routine {
    return RoutineEntityMapper.asDomain(this)
}