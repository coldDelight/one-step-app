package com.colddelight.network.datasourceImpl

import com.colddelight.network.datasource.UserDataSource
import com.colddelight.network.model.User
import com.colddelight.network.SupabaseClient.client
import io.github.jan.supabase.postgrest.postgrest

class UserDataSourceImpl() : UserDataSource {
    override suspend fun getUserInfo(): User {
        return client.postgrest["user"].select().decodeSingle<User>()
    }

}
