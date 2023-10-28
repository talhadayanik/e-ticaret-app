package com.example.e_ticaretapp.ui.details

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.e_ticaretapp.MainActivity
import com.example.e_ticaretapp.databinding.FragmentDetailsBinding
import com.example.e_ticaretapp.ui.cart.CartViewModel

class DetailsFragment : Fragment() {

    companion object {
        fun newInstance() = DetailsFragment()
    }

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: DetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        val view = binding.root

        val selectedProduct = MainActivity.selectedProduct

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
            Toast.makeText(requireContext(), "Added to cart", Toast.LENGTH_LONG).show()
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(DetailsViewModel::class.java)
        // TODO: Use the ViewModel
    }

}