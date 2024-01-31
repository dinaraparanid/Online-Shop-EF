package com.paranid5.emonlineshop.presentation.main.fragments.bag

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.paranid5.emonlineshop.R
import com.paranid5.emonlineshop.databinding.FragmentBagBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class BagFragment : Fragment() {
    private lateinit var binding: FragmentBagBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_bag, container, false)
        return binding.root
    }
}