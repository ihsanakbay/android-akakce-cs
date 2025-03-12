package com.ihsanakbay.android_akakce_cs.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ihsanakbay.android_akakce_cs.R
import com.ihsanakbay.android_akakce_cs.data.models.Product

class VerticalProductAdapter(
    private val products: List<Product>,
    private val onItemClick: (Product) -> Unit
): RecyclerView.Adapter<VerticalProductAdapter.ProductViewHolder>() {
    class ProductViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.ivVerticalProductImage)
        val productTitle: TextView = itemView.findViewById(R.id.tvVerticalProductTitle)
        val productPrice: TextView = itemView.findViewById(R.id.tvVerticalProductPrice)
        val productRating: RatingBar = itemView.findViewById(R.id.rbVerticalProductRating)
        val productCategory: TextView = itemView.findViewById(R.id.tvVerticalProductCategory)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_vertical_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]

        holder.productTitle.text = product.title
        holder.productPrice.text = "$${product.price}"
        holder.productRating.rating = product.rating.rate.toFloat()
        holder.productCategory.text = product.category

        Glide.with(holder.itemView.context)
            .load(product.image)
            .centerCrop()
            .into(holder.productImage)

        holder.itemView.setOnClickListener {
            onItemClick(product)
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }

}