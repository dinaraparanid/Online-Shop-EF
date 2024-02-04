package com.paranid5.emonlineshop.presentation.main.fragments.profile

import androidx.lifecycle.ViewModel
import com.paranid5.emonlineship.data.config.ConfigRepository
import com.paranid5.emonlineship.data.config.sources.user.UserPublisher
import com.paranid5.emonlineship.data.config.sources.user.UserPublisherImpl
import com.paranid5.emonlineship.data.config.sources.user.UserSubscriber
import com.paranid5.emonlineship.data.config.sources.user.UserSubscriberImpl
import com.paranid5.emonlineship.data.favourites.FavouritesDataSource
import com.paranid5.emonlineship.data.favourites.FavouritesSubscriber
import com.paranid5.emonlineship.data.favourites.FavouritesSubscriberImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.mapLatest
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    configRepository: ConfigRepository,
    favouriteDataSource: FavouritesDataSource
) : ViewModel(),
    UserSubscriber by UserSubscriberImpl(configRepository),
    UserPublisher by UserPublisherImpl(configRepository),
    FavouritesSubscriber by FavouritesSubscriberImpl(favouriteDataSource)

@OptIn(ExperimentalCoroutinesApi::class)
inline val ProfileViewModel.favouritesNumberFlow
    get() = favouritesFlow.mapLatest { it.size }