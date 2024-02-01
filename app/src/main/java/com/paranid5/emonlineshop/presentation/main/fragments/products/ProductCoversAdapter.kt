package com.paranid5.emonlineshop.presentation.main.fragments.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.recyclerview.widget.RecyclerView
import com.paranid5.emonlineshop.databinding.ItemProductCoverBinding
import com.paranid5.emonlineshop.presentation.ui.getDrawableCompat

class ProductCoversAdapter(private val covers: List<Int>) :
    RecyclerView.Adapter<ProductCoversAdapter.ProductCoversViewHolder>() {
    class ProductCoversViewHolder(private val binding: ItemProductCoverBinding) :
        RecyclerView.ViewHolder(binding.root) {
        infix fun bind(@DrawableRes coverRes: Int): Unit =
            binding.productCover.run {
                setImageDrawable(context.getDrawableCompat(coverRes))
            }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductCoversViewHolder =
        ProductCoversViewHolder(
            ItemProductCoverBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ProductCoversViewHolder, position: Int) {
        holder bind covers[position]
    }

    override fun getItemCount(): Int = covers.size
}