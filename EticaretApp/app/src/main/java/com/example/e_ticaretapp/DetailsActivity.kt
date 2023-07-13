package com.example.e_ticaretapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.e_ticaretapp.databinding.ActivityDetailsBinding
import com.bumptech.glide.Glide
import com.example.e_ticaretapp.ui.cart.CartFragment
import com.example.e_ticaretapp.ui.cart.CartViewModel
import com.google.android.material.snackbar.Snackbar

class DetailsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val selectedProduct = MainActivity.selectedProduct

        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        Glide.with(this).load(selectedProduct.images.get(0)).into(binding.imgDetail)
        binding.txtDetailTitle.text = selectedProduct.title
        binding.txtDetailRate.text = "${selectedProduct.rating} / 5"
        binding.txtDetailDesc.text = selectedProduct.description
        binding.txtDetailBrand.text = "Brand: ${selectedProduct.brand}"
        binding.txtDetailCategory.text = "Category: ${selectedProduct.category}"
        binding.txtDetailStock.text = "Stock: ${selectedProduct.stock}"
        binding.txtDetailDiscount.text = "Discount: %${selectedProduct.discountPercentage}}"
        binding.txtDetailPrice.text = "${selectedProduct.price}$"

        binding.btnAddToCart.setOnClickListener {
            CartViewModel.cartProducts.add(selectedProduct)
            Toast.makeText(this, "Added to cart", Toast.LENGTH_LONG).show()
        }
    }
}