package com.ihsanakbay.android_akakce_cs.ui.productDetail

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.RatingBar
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.ihsanakbay.android_akakce_cs.R
import com.ihsanakbay.android_akakce_cs.utils.Constants

class ProductDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: ProductDetailViewModel
    private lateinit var ivProductImage: ImageView
    private lateinit var tvProductTitle: TextView
    private lateinit var tvProductCategory: TextView
    private lateinit var tvProductPrice: TextView
    private lateinit var tvProductDescription: TextView
    private lateinit var rbProductRating: RatingBar
    private lateinit var tvRatingCount: TextView
    private lateinit var progressBar: ProgressBar
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_detail)

        initViews()

        viewModel = ViewModelProvider(this)[ProductDetailViewModel::class.java]

        val productId = intent.getIntExtra(Constants.PRODUCT_ID_KEY, -1)
        if (productId == -1) {
            Toast.makeText(this, "Invalid product id", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        setupObservers()
        viewModel.fetchProductDetail(productId)
    }

    private fun initViews() {
        ivProductImage = findViewById(R.id.ivProductImage)
        tvProductTitle = findViewById(R.id.tvProductTitle)
        tvProductCategory = findViewById(R.id.tvProductCategory)
        tvProductPrice = findViewById(R.id.tvProductPrice)
        tvProductDescription = findViewById(R.id.tvProductDescription)
        rbProductRating = findViewById(R.id.rbProductRating)
        tvRatingCount = findViewById(R.id.tvRatingCount)
        progressBar = findViewById(R.id.progressBar)
        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.title = "Product Details"
    }

    private fun setupObservers() {
        viewModel.product.observe(this) { product ->
            Glide.with(this)
                .load(product.image)
                .into(ivProductImage)

            tvProductTitle.text = product.title
            tvProductCategory.text = product.category
            tvProductPrice.text = "$${product.price}"
            tvProductDescription.text = product.description
            rbProductRating.rating = product.rating.rate.toFloat()
            tvRatingCount.text = "(${product.rating.count} reviews)"

        }

        viewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(this) { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}