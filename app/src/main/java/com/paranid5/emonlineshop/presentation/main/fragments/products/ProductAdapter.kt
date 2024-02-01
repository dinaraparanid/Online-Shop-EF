package com.paranid5.emonlineshop.presentation.main.fragments.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.tabs.TabLayoutMediator
import com.paranid5.emonlineshop.R
import com.paranid5.emonlineshop.databinding.ItemProductBinding
import com.paranid5.emonlineshop.domain.product.IProduct
import com.paranid5.emonlineshop.domain.product.equalTo

class ProductsAdapter : RecyclerView.Adapter<ProductsAdapter.ProductsViewHolder>() {
    private val differ by lazy {
        AsyncListDiffer(this, DiffCallback())
    }

    private inline val currentList: List<IProduct>
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

    infix fun submitList(products: List<IProduct>): Unit =
        differ.submitList(products)

    class ProductsViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        infix fun bind(product: IProduct) {
            binding.product = product
            binding.productCoverPager.adapter = ProductCoversAdapter(product.coversRes)
            TabLayoutMediator(binding.productTab, binding.productCoverPager) { _, _ -> }.attach()
            binding.executePendingBindings()
        }
    }
}

private fun DiffCallback() =
    object : DiffUtil.ItemCallback<IProduct>() {
        override fun areItemsTheSame(oldItem: IProduct, newItem: IProduct): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: IProduct, newItem: IProduct): Boolean =
            oldItem equalTo newItem
    }