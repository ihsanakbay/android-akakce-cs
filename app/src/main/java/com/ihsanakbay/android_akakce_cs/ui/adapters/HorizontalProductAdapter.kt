package com.ihsanakbay.android_akakce_cs.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.ihsanakbay.android_akakce_cs.data.models.Product
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ihsanakbay.android_akakce_cs.R
import java.security.PrivateKey

class HorizontalProductAdapter(
    private val products: List<Product>,
    private val onItemClick: (Product) -> Unit
) : RecyclerView.Adapter<HorizontalProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val productImage: ImageView = itemView.findViewById(R.id.ivHorizontalProductImage)
        val productTitle: TextView = itemView.findViewById(R.id.tvHorizontalProductTitle)
        val productPrice: TextView = itemView.findViewById(R.id.tvHorizontalProductPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_horizontal_product, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products[position]

        holder.productTitle.text = product.title
        holder.productPrice.text = "$${product.price}"

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