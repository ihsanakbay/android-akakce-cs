package com.ihsanakbay.android_akakce_cs.ui.productList

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ihsanakbay.android_akakce_cs.R
import com.ihsanakbay.android_akakce_cs.ui.adapters.HorizontalProductAdapter
import com.ihsanakbay.android_akakce_cs.ui.adapters.VerticalProductAdapter
import com.ihsanakbay.android_akakce_cs.ui.productDetail.ProductDetailActivity
import com.ihsanakbay.android_akakce_cs.utils.Constants

class ProductListActivity : AppCompatActivity() {
    private lateinit var viewModel: ProductListViewModel
    private lateinit var rvHorizontalProducts: RecyclerView
    private lateinit var rvVerticalProducts: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_list)

        initViews()

        viewModel = ViewModelProvider(this)[ProductListViewModel::class.java]

        setupRecyclerViews()
        observeViewModel()
    }

    private fun initViews() {
        rvHorizontalProducts = findViewById(R.id.rvHorizontalProducts)
        rvVerticalProducts = findViewById(R.id.rvVerticalProducts)
        progressBar = findViewById(R.id.progressBar)
        toolbar = findViewById(R.id.toolbar)

        setSupportActionBar(toolbar)
    }

    private fun setupRecyclerViews() {
        rvHorizontalProducts.layoutManager = LinearLayoutManager(
            this, LinearLayoutManager.HORIZONTAL, false
        )

        rvVerticalProducts.layoutManager = GridLayoutManager(this, 2)
    }


    private fun observeViewModel() {
        viewModel.horizontalProducts.observe(this) { products ->
            rvHorizontalProducts.adapter = HorizontalProductAdapter(products) { product ->
                navigateToProductDetail(product.id)
            }
        }

        viewModel.verticalProducts.observe(this) { products ->
            rvVerticalProducts.adapter = VerticalProductAdapter(products) { product ->
                navigateToProductDetail(product.id)
            }
        }

        viewModel.isLoading.observe(this) { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        }

        viewModel.error.observe(this) { errorMessage ->
            Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show()
        }
    }

    private fun navigateToProductDetail(productId: Int) {
        val intent = Intent()
        intent.setClass(this, ProductDetailActivity::class.java)
        intent.putExtra(Constants.PRODUCT_ID_KEY, productId)
        startActivity(intent)
    }
}