package com.paranid5.emonlineshop.presentation.main.fragments.favourites

import androidx.lifecycle.ViewModel
import com.paranid5.emonlineship.data.favourites.FavouritesDataSource
import com.paranid5.emonlineship.data.favourites.FavouritesPublisher
import com.paranid5.emonlineship.data.favourites.FavouritesPublisherImpl
import com.paranid5.emonlineship.data.favourites.FavouritesSubscriber
import com.paranid5.emonlineship.data.favourites.FavouritesSubscriberImpl
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavouritesViewModel @Inject constructor(favouritesDataSource: FavouritesDataSource) :
    ViewModel(),
    FavouritesSubscriber by FavouritesSubscriberImpl(favouritesDataSource),
    FavouritesPublisher by FavouritesPublisherImpl(favouritesDataSource)