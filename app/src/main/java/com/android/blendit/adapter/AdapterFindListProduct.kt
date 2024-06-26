package com.android.blendit.adapter

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.android.blendit.databinding.ItemProductBinding
import com.android.blendit.remote.response.FindItems
import com.android.blendit.remote.response.ItemsFavorite
import com.android.blendit.ui.activity.detail.DetailActivity
import com.bumptech.glide.Glide

class AdapterFindListProduct :
    PagingDataAdapter<FindItems, AdapterFindListProduct.ViewHolder>(DIFF_CALLBACK) {

    private val listFavorite = mutableListOf<ItemsFavorite>()

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<ItemsFavorite>) {
        listFavorite.clear()
        listFavorite.addAll(list)
        notifyDataSetChanged()
    }

    inner class ViewHolder(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: FindItems) {
            with(binding) {
                Glide.with(itemView).load(product.picture).into(imageView)
                brand.text = product.brand
                productName.text = product.productName

                itemView.setOnClickListener {
                    val intent = Intent(itemView.context, DetailActivity::class.java).apply {
                        putExtra(DetailActivity.ID, product.id)
                        putExtra(DetailActivity.BRAND, product.brand)
                        putExtra(DetailActivity.PRODUCT_NAME, product.productName)
                        putExtra(DetailActivity.PICTURE, product.picture)
                        putExtra(DetailActivity.TYPE, product.type)
                        putExtra(DetailActivity.SKINTONE, product.skintone)
                        putExtra(DetailActivity.SKINTYPE, product.skinType)
                        putExtra(DetailActivity.UNDERTONE, product.undertone)
                        putExtra(DetailActivity.SHADE, product.shade)
                        putExtra(DetailActivity.MAKEUPTYPE, product.makeupType)
                        for (favorite in listFavorite) {
                            if (favorite.productId == product.id) {
                                putExtra(DetailActivity.IS_FAVORITE, true)
                            }
                        }
                    }
                    it.context.startActivity(intent)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FindItems>() {
            override fun areItemsTheSame(oldItem: FindItems, newItem: FindItems): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: FindItems, newItem: FindItems): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}