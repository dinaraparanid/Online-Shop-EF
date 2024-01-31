package com.paranid5.emonlineshop.presentation.main.fragments.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.paranid5.emonlineshop.R
import com.paranid5.emonlineshop.databinding.FragmentCatalogBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CatalogFragment : Fragment() {
    private lateinit var binding: FragmentCatalogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_catalog, container, false)
        return binding.root
    }
}