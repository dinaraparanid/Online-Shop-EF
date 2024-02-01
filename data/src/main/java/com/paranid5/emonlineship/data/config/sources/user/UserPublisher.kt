package com.paranid5.emonlineship.data.config.sources.user

import com.paranid5.emonlineship.data.config.ConfigRepository
import com.paranid5.emonlineshop.domain.User

interface UserPublisher {
    suspend fun storeUser(user: User)
}

class UserPublisherImpl(private val configRepository: ConfigRepository) : UserPublisher {
    override suspend fun storeUser(user: User) =
        configRepository.storeUser(user)
}