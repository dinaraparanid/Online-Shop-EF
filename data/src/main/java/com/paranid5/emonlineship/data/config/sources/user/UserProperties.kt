package com.paranid5.emonlineship.data.config.sources.user

import com.paranid5.emonlineship.data.config.ConfigRepository
import com.paranid5.emonlineshop.domain.User
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.mapLatest

inline val ConfigRepository.userOrNullFlow: Flow<User?>
    get() = userDataSource.userFlow

inline val ConfigRepository.userFlow: Flow<User>
    get() = userOrNullFlow.filterNotNull()

suspend inline fun ConfigRepository.storeUser(user: User): Unit =
    userDataSource.storeUser(user)

@OptIn(ExperimentalCoroutinesApi::class)
inline val UserSubscriber.userNameFamilyFlow: Flow<String>
    get() = userFlow.mapLatest { "${it.name} ${it.family}" }

@OptIn(ExperimentalCoroutinesApi::class)
inline val UserSubscriber.userPhoneFlow: Flow<String>
    get() = userFlow.mapLatest { it.phone }