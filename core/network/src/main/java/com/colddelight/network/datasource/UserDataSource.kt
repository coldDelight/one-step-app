package com.colddelight.network.datasource

import com.colddelight.network.model.User

interface UserDataSource {
    suspend fun getUserInfo():User
}