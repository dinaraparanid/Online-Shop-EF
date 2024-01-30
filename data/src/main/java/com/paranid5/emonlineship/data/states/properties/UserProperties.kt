package com.paranid5.emonlineship.data.states.properties

import com.paranid5.emonlineship.data.StorageHandler
import com.paranid5.emonlineshop.domain.User

inline val StorageHandler.userFlow
    get() = userProvider.userFlow

suspend inline fun StorageHandler.storeUser(user: User) =
    userProvider.storeUser(user)