package com.paranid5.emonlineship.data.states.properties

import com.paranid5.emonlineship.data.ConfigDataSource
import com.paranid5.emonlineshop.domain.User

inline val ConfigDataSource.userFlow
    get() = userProvider.userFlow

suspend inline fun ConfigDataSource.storeUser(user: User) =
    userProvider.storeUser(user)