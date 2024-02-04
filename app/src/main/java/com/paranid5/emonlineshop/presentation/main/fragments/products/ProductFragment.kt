package com.paranid5.emonlineshop.presentation.main.fragments.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.paranid5.emonlineship.data.favourites.FavouritesDataSource
import com.paranid5.emonlineshop.R
import com.paranid5.emonlineshop.databinding.FragmentProductBinding
import com.paranid5.emonlineshop.domain.product.IProduct
import com.paranid5.emonlineshop.presentation.main.MainActivity
import com.paranid5.emonlineshop.presentation.ui.LineDivider
import com.paranid5.emonlineshop.presentation.ui.getDrawableCompat
import com.paranid5.emonlineshop.presentation.ui.launchOnStarted
import com.paranid5.emonlineshop.presentation.utils.availableOnRussianRes
import com.paranid5.emonlineshop.presentation.utils.feedbackOnRussianRes
import com.paranid5.emonlineshop.presentation.utils.getParcelableCompat
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class ProductFragment : Fragment() {
    private lateinit var binding: FragmentProductBinding

    @Inject
    internal lateinit var favouritesDataSource: FavouritesDataSource

    private val viewModel by lazy {
        ProductViewModel(
            product = requireArguments().getParcelableCompat(
                PRODUCT_ARG,
                IProduct::class.java
            ),
            favouritesDataSource = favouritesDataSource
        )
    }

    private val infoAdapter by lazy {
        ProductInfoAdapter(viewModel.info).apply {
            stateRestorationPolicy = RecyclerView.Adapter.StateRestorationPolicy.PREVENT_WHEN_EMPTY
        }
    }

    companion object {
        private const val PRODUCT_ARG = "product"

        fun argumentsBundle(product: IProduct): Bundle =
            Bundle().apply {
                putParcelable(PRODUCT_ARG, product)
            }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_product,
            container,
            false
        )

        binding.viewModel = viewModel
        initViews()
        launchLikeMonitoring()
        return binding.root
    }

    private fun initViews() {
        setupCoversPager()
        setupInfoList()

        binding.availableText.text = getString(availableOnRussianRes(viewModel.available), viewModel.available)
        binding.feedbackText.text = getString(feedbackOnRussianRes(viewModel.feedbackCount), viewModel.feedbackCount)

        binding.backButton.setOnClickListener {
            (requireActivity() as MainActivity)
                .navigateToPreviousFragment()
        }

        binding.showDescription.setOnClickListener {
            viewModel.onDescriptionShownClicked()
        }

        binding.showIngredients.setOnClickListener {
            viewModel.onIngredientsShownClicked()
        }
    }

    private fun setupCoversPager() {
        binding.productCoverPager.adapter = ProductCoversAdapter(viewModel.coversRes)
        TabLayoutMediator(binding.productTab, binding.productCoverPager) { _, _ -> }.attach()
    }

    private fun setupInfoList() =
        binding.infoList.run {
            adapter = infoAdapter

            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = LinearLayoutManager.VERTICAL
            }

            addItemDecoration(LineDivider(requireContext()))
        }

    private fun launchLikeMonitoring() =
        launchOnStarted {
            viewModel.likeFlow.collectLatest { isLiked ->
                binding.likeButton.setImageDrawable(
                    requireContext().getDrawableCompat(
                        viewModel.likeImage(isLiked)
                    )
                )

                binding.likeButton.setOnClickListener {
                    lifecycleScope.launch(Dispatchers.IO) {
                        viewModel.onLikeClicked(isLiked)
                    }
                }
            }
        }
}