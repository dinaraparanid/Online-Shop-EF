package com.paranid5.emonlineshop.presentation.main.fragments.favourites

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import com.paranid5.emonlineshop.R
import com.paranid5.emonlineshop.databinding.FragmentFavouritesBinding
import com.paranid5.emonlineshop.presentation.main.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FavouritesFragment : Fragment() {
    private lateinit var binding: FragmentFavouritesBinding
    private lateinit var fragmentsAdapter: FavouritesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_favourites,
            container,
            false
        )

        initViews()
        return binding.root
    }

    private fun initViews() {
        setupFragmentPager()

        binding.backButton.setOnClickListener {
            (requireActivity() as MainActivity).navigateToProfileFragment()
        }
    }

    private fun setupFragmentPager() {
        fragmentsAdapter = FavouritesAdapter(this)
        binding.fragmentsPager.adapter = fragmentsAdapter

        TabLayoutMediator(binding.fragmentsTab, binding.fragmentsPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.products)
                1 -> getString(R.string.brands)
                else -> throw IllegalStateException("Unknown favourite fragment position")
            }
        }.attach()
    }
}