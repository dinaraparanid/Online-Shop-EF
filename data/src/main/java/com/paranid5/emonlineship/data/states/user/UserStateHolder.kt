package com.paranid5.emonlineship.data.states.user

import com.paranid5.emonlineship.data.ConfigDataSource
import com.paranid5.emonlineship.data.states.properties.storeUser
import com.paranid5.emonlineship.data.states.properties.userFlow
import com.paranid5.emonlineshop.domain.User
import kotlinx.coroutines.flow.Flow

interface UserSubscriber {
    val userFlow: Flow<User?>
}

interface UserPublisher {
    suspend fun storeUser(user: User)
}

class UserSubscriberImpl(private val configDataSource: ConfigDataSource) : UserSubscriber {
    override val userFlow: Flow<User?>
        get() = configDataSource.userFlow
}

class UserPublisherImpl(private val configDataSource: ConfigDataSource) : UserPublisher {
    override suspend fun storeUser(user: User) =
        configDataSource.storeUser(user)
}