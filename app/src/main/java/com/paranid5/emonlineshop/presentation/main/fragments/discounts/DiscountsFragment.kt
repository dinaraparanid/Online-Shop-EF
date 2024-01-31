package com.paranid5.emonlineshop.presentation.main.fragments.discounts

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.paranid5.emonlineshop.R
import com.paranid5.emonlineshop.databinding.FragmentDiscountsBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DiscountsFragment : Fragment() {
    private lateinit var binding: FragmentDiscountsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_discounts, container, false)
        return binding.root
    }
}