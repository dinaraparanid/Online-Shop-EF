package com.paranid5.emonlineshop.presentation.main.fragments.products

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.paranid5.emonlineship.data.favourites.FavouritesPublisher
import com.paranid5.emonlineshop.R
import com.paranid5.emonlineshop.databinding.ItemProductBinding
import com.paranid5.emonlineshop.domain.product.IProduct
import com.paranid5.emonlineshop.domain.product.ProductWithLike
import com.paranid5.emonlineshop.presentation.ui.getDrawableCompat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductsAdapter(
    private val favouritesPublisher: FavouritesPublisher,
    private val onItemClicked: (IProduct) -> Unit
) :
    RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>(),
    CoroutineScope by CoroutineScope(Dispatchers.Main) {
    private val differ by lazy {
        AsyncListDiffer(this, DiffCallback())
    }

    private inline val currentList: List<ProductWithLike>
        get() = differ.currentList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder =
        DataBindingUtil.inflate<ItemProductBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_product,
            parent,
            false
        ).let(::ProductsViewHolder)

    override fun getItemCount(): Int =
        currentList.size

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int): Unit =
        holder bind currentList[position]

    infix fun submitList(products: List<ProductWithLike>): Unit =
        differ.submitList(products)

    inner class ProductsViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        infix fun bind(prodLike: ProductWithLike) {
            binding.product = prodLike.product
            setLikeImage(prodLike.isLiked)

            binding.productCoverPager.adapter = ProductCoversAdapter(prodLike.product.coversUrls)
            TabLayoutMediator(binding.productTab, binding.productCoverPager) { _, _ -> }.attach()

            binding.productLike.setOnClickListener {
                onLikeClicked(prodLike)
            }

            binding.itemLayout.setOnClickListener {
                onItemClicked(prodLike.product)
            }

            binding.executePendingBindings()
        }

        private fun onLikeClicked(prodLike: ProductWithLike) =
            launch(Dispatchers.IO) {
                when {
                    prodLike.isLiked -> favouritesPublisher.removeFavourite(prodLike.product.id)
                    else -> favouritesPublisher.addFavourite(prodLike.product)
                }
            }

        private fun setLikeImage(isLiked: Boolean) =
            setLikeImage(if (isLiked) R.drawable.heart_liked else R.drawable.heart)

        private fun setLikeImage(@DrawableRes heartRes: Int) =
            binding.productLike.run {
                setImageDrawable(context.getDrawableCompat(heartRes))
            }
    }
}

val IProduct.ratingVisibility: Int
    get() = when (feedback.count) {
        0L -> View.GONE
        else -> View.VISIBLE
    }

private fun DiffCallback() =
    object : DiffUtil.ItemCallback<ProductWithLike>() {
        override fun areItemsTheSame(oldItem: ProductWithLike, newItem: ProductWithLike): Boolean =
            oldItem.product.id == newItem.product.id

        override fun areContentsTheSame(oldItem: ProductWithLike, newItem: ProductWithLike): Boolean =
            oldItem == newItem
    }