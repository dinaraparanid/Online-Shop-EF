package com.paranid5.emonlineship.data.states.user

import com.paranid5.emonlineship.data.StorageHandler
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

class UserSubscriberImpl(private val storageHandler: StorageHandler) : UserSubscriber {
    override val userFlow: Flow<User?>
        get() = storageHandler.userFlow
}

class UserPublisherImpl(private val storageHandler: StorageHandler) : UserPublisher {
    override suspend fun storeUser(user: User) =
        storageHandler.storeUser(user)
}