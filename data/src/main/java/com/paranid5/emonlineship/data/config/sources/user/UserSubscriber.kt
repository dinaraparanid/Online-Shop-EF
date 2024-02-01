package com.paranid5.emonlineship.data.config.sources.user

import com.paranid5.emonlineship.data.config.ConfigRepository
import com.paranid5.emonlineshop.domain.User
import kotlinx.coroutines.flow.Flow

interface UserSubscriber {
    val userFlow: Flow<User>
}

class UserSubscriberImpl(private val configRepository: ConfigRepository) : UserSubscriber {
    override val userFlow: Flow<User>
        get() = configRepository.userFlow
}