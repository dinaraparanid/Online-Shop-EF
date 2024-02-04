package com.paranid5.emonlineshop.presentation.main.fragments.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.paranid5.emonlineshop.R
import com.paranid5.emonlineshop.databinding.FragmentCatalogBinding
import com.paranid5.emonlineshop.domain.product.ProductOrder
import com.paranid5.emonlineshop.presentation.main.MainActivity
import com.paranid5.emonlineshop.presentation.main.fragments.products.ProductsAdapter
import com.paranid5.emonlineshop.presentation.main.fragments.products.ProductsTagsAdapter
import com.paranid5.emonlineshop.presentation.ui.PaddingItemDecorator
import com.paranid5.emonlineshop.presentation.ui.launchOnStarted
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.math.roundToInt

@AndroidEntryPoint
class CatalogFragment : Fragment() {
    private lateinit var binding: FragmentCatalogBinding

    private val viewModel by viewModels<CatalogViewModel>()

    private val productTagsAdapter by lazy {
        ProductsTagsAdapter(
            selectTag = viewModel::selectTag,
            unselectTag = viewModel::unselectTag
        ).apply {
            stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }
    }

    private val productsAdapter by lazy {
        ProductsAdapter(viewModel) {
            (requireActivity() as MainActivity)
                .navigateToProductFragment<CatalogFragment>(it)
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
            R.layout.fragment_catalog,
            container,
            false
        )

        initViews()
        fetchProducts()

        launchProductsMonitoring()
        launchTagsMonitoring()

        return binding.root
    }

    private fun initViews() {
        initTagList()
        initCatalogList()
        initSortSpinner()
    }

    private fun initTagList() =
        binding.tagList.run {
            adapter = productTagsAdapter

            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.HORIZONTAL
            }

            addItemDecoration(
                PaddingItemDecorator(
                    leftPadding = resources
                        .getDimension(R.dimen.tags_padding)
                        .roundToInt()
                )
            )
        }

    private fun initCatalogList() =
        binding.catalogList.run {
            adapter = productsAdapter

            layoutManager = GridLayoutManager(requireContext(), 2).apply {
                orientation = LinearLayoutManager.VERTICAL
            }

            addItemDecoration(
                PaddingItemDecorator(
                    topPadding = resources
                        .getDimension(R.dimen.products_padding)
                        .roundToInt()
                )
            )
        }

    private fun initSortSpinner() =
        binding.sortSpinner.run {
            selectItemByIndex(0)

            setOnSpinnerItemSelectedListener<String> { _, _, newIndex, _ ->
                viewModel setProductOrder ProductOrder.entries[newIndex]
            }
        }

    private fun fetchProducts() =
        lifecycleScope.launch(Dispatchers.IO) {
            viewModel.fetchProductsAndTags(requireContext())
        }

    private fun launchProductsMonitoring() = launchOnStarted {
        viewModel.productsFlow.collectLatest {
            productsAdapter submitList it
        }
    }

    private fun launchTagsMonitoring() = launchOnStarted {
        viewModel.tagsState.collectLatest {
            productTagsAdapter submitList it
        }
    }
}