package com.paranid5.emonlineshop.presentation.main.fragments.products

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.paranid5.emonlineshop.databinding.ItemProductTagBinding
import com.paranid5.emonlineshop.databinding.ItemProductTagSelectedBinding
import com.paranid5.emonlineshop.domain.product.tag.TagWithSelect
import com.paranid5.emonlineshop.domain.product.tag.translateTag

class ProductsTagsAdapter(
    private val selectTag: (tag: String) -> Unit,
    private val unselectTag: (tag: String) -> Unit
) : RecyclerView.Adapter<ProductsTagsAdapter.ProductsTagsViewHolder>() {
    private enum class SelectionViewType {
        SELECTED, NON_SELECTED
    }

    private val differ by lazy {
        AsyncListDiffer(this, DiffCallback())
    }

    private inline val currentList: List<TagWithSelect>
        get() = differ.currentList

    override fun getItemViewType(position: Int): Int = when {
        currentList[position].isSelected -> SelectionViewType.SELECTED
        else -> SelectionViewType.NON_SELECTED
    }.ordinal

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsTagsViewHolder =
        ProductsTagsViewHolder(
            when (SelectionViewType.entries[viewType]) {
                SelectionViewType.SELECTED -> ItemProductTagSelectedBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )

                SelectionViewType.NON_SELECTED -> ItemProductTagBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            }
        )

    override fun onBindViewHolder(holder: ProductsTagsViewHolder, position: Int) {
        val (tag, _) = currentList[position]
        val translatedTag = holder.binding.root.context.translateTag(tag)

        when (val binding = holder.binding) {
            is ItemProductTagSelectedBinding ->
                holder.bind {
                    binding.tag = translatedTag
                    binding.cardView.setOnClickListener { unselectTag(tag) }
                    binding.cancelTag.setOnClickListener { unselectTag(tag) }
                }

            is ItemProductTagBinding -> holder.bind {
                binding.tag = translatedTag
                binding.cardView.setOnClickListener { selectTag(tag) }
            }
        }
    }

    override fun getItemCount(): Int = currentList.size

    infix fun submitList(products: List<TagWithSelect>): Unit =
        differ.submitList(products)

    class ProductsTagsViewHolder(val binding: ViewDataBinding) :
        RecyclerView.ViewHolder(binding.root) {
        internal inline fun bind(applyTagAndPrepareBinding: () -> Unit) {
            applyTagAndPrepareBinding()
            binding.executePendingBindings()
        }
    }
}

private fun DiffCallback() =
    object : DiffUtil.ItemCallback<TagWithSelect>() {
        override fun areItemsTheSame(oldItem: TagWithSelect, newItem: TagWithSelect): Boolean =
            oldItem.tag == newItem.tag

        override fun areContentsTheSame(oldItem: TagWithSelect, newItem: TagWithSelect): Boolean =
            oldItem.isSelected == newItem.isSelected
    }