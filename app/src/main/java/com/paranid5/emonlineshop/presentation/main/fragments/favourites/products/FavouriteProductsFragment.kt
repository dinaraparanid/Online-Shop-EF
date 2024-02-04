package com.paranid5.emonlineshop.presentation.main.fragments.favourites.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paranid5.emonlineshop.R
import com.paranid5.emonlineshop.databinding.FragmentFavouriteProductsBinding
import com.paranid5.emonlineshop.domain.product.ProductWithLike
import com.paranid5.emonlineshop.presentation.main.MainActivity
import com.paranid5.emonlineshop.presentation.main.fragments.products.ProductsAdapter
import com.paranid5.emonlineshop.presentation.main.fragments.profile.ProfileFragment
import com.paranid5.emonlineshop.presentation.ui.PaddingItemDecorator
import com.paranid5.emonlineshop.presentation.ui.launchOnStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.mapLatest

@AndroidEntryPoint
class FavouriteProductsFragment : Fragment() {
    private lateinit var binding: FragmentFavouriteProductsBinding

    private val viewModel by viewModels<FavouriteProductsViewModel>()

    private val productsAdapter by lazy {
        ProductsAdapter(viewModel) {
            (requireActivity() as MainActivity)
                .navigateToProductFragment<ProfileFragment>(it)
        }.apply {
            stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_favourite_products,
            container,
            false
        )

        initViews()
        launchFavouritesMonitoring()
        return binding.root
    }

    private fun initViews() =
        binding.favouritesView.run {
            adapter = productsAdapter

            layoutManager = GridLayoutManager(requireContext(), 2).apply {
                orientation = LinearLayoutManager.VERTICAL
            }

            addItemDecoration(PaddingItemDecorator(topPadding = 7))
        }

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun launchFavouritesMonitoring() =
        launchOnStarted {
            viewModel
                .favouritesFlow
                .mapLatest { favs ->
                    favs.map { ProductWithLike(it, isLiked = true) }
                }
                .collectLatest {
                    productsAdapter submitList it
                }
        }
}