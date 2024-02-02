package com.paranid5.emonlineshop.presentation.main.fragments.catalog

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
import com.paranid5.emonlineshop.databinding.FragmentCatalogBinding
import com.paranid5.emonlineshop.domain.product.FavouriteProduct
import com.paranid5.emonlineshop.domain.product.ProductWithLike
import com.paranid5.emonlineshop.presentation.main.fragments.products.ProductsAdapter
import com.paranid5.emonlineshop.presentation.ui.PaddingItemDecorator
import com.paranid5.emonlineshop.presentation.ui.launchOnStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@AndroidEntryPoint
class CatalogFragment : Fragment() {
    private lateinit var binding: FragmentCatalogBinding

    private val viewModel by viewModels<CatalogViewModel>()

    private val productsAdapter by lazy {
        ProductsAdapter(viewModel).apply {
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
            R.layout.fragment_catalog,
            container,
            false
        )

        initViews()
        fetchAndSubmitProducts()
        return binding.root
    }

    private fun initViews() {
        binding.catalogList.run {
            adapter = productsAdapter

            layoutManager = GridLayoutManager(requireContext(), 2).apply {
                orientation = LinearLayoutManager.VERTICAL
            }

            addItemDecoration(PaddingItemDecorator(topPadding = 7))
        }
    }

    private fun fetchAndSubmitProducts() =
        launchOnStarted {
            launch(Dispatchers.IO) {
                viewModel.fetchProducts()
            }

            combine(viewModel.productsState, viewModel.favouritesFlow) { network, db ->
                withContext(Dispatchers.IO) {
                    (network + db)
                        .groupBy { it.id }
                        .map { (_, products) ->
                            products
                                .find { it is FavouriteProduct }
                                ?.let { ProductWithLike(it, isLiked = true) }
                                ?: ProductWithLike(products.first(), isLiked = false)
                        }
                }
            }.collectLatest {
                productsAdapter submitList it
            }
        }
}