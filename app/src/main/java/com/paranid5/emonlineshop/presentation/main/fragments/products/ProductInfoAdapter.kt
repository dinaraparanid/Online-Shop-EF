package com.paranid5.emonlineshop.presentation.main.fragments.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.paranid5.emonlineshop.databinding.ItemProductInfoBinding
import com.paranid5.emonlineshop.domain.product.Info

class ProductInfoAdapter(private val info: List<Info>) :
    RecyclerView.Adapter<ProductInfoAdapter.ProductInfoViewHolder>() {
    class ProductInfoViewHolder(private val binding: ItemProductInfoBinding) :
        RecyclerView.ViewHolder(binding.root) {
        infix fun bind(info: Info) {
            binding.info = info
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductInfoViewHolder =
        ProductInfoViewHolder(
            ItemProductInfoBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

    override fun onBindViewHolder(holder: ProductInfoViewHolder, position: Int): Unit =
        holder bind info[position]

    override fun getItemCount(): Int = info.size
}