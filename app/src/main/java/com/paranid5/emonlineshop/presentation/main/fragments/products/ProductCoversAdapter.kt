package com.paranid5.emonlineshop.presentation.main.fragments.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.paranid5.emonlineshop.databinding.ItemProductCoverBinding

class ProductCoversAdapter(private val covers: List<String>) :
    RecyclerView.Adapter<ProductCoversAdapter.ProductCoversViewHolder>() {
    class ProductCoversViewHolder(private val binding: ItemProductCoverBinding) :
        RecyclerView.ViewHolder(binding.root) {
        infix fun bind(coverUrl: String) {
            binding.productCover.load(coverUrl) {
                crossfade(300)
            }
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

    override fun onBindViewHolder(holder: ProductCoversViewHolder, position: Int): Unit =
        holder bind covers[position]

    override fun getItemCount(): Int = covers.size
}