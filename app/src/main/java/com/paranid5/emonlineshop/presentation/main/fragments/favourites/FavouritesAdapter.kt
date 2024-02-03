package com.paranid5.emonlineshop.presentation.main.fragments.favourites

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.paranid5.emonlineshop.presentation.main.fragments.favourites.brands.FavouriteBrandsFragment
import com.paranid5.emonlineshop.presentation.main.fragments.favourites.products.FavouriteProductsFragment

class FavouritesAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {
    override fun createFragment(position: Int): Fragment =
        when (position) {
            0 -> FavouriteProductsFragment()
            1 -> FavouriteBrandsFragment()
            else -> throw IllegalStateException("Unknown favourite fragment position")
        }

    override fun getItemCount(): Int = 2
}