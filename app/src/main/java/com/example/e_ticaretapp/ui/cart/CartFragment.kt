package com.example.e_ticaretapp.ui.cart

import android.graphics.drawable.ColorDrawable
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.e_ticaretapp.MainActivity
import com.example.e_ticaretapp.R
import com.example.e_ticaretapp.adapters.ProductsAdapter
import com.example.e_ticaretapp.databinding.FragmentCartBinding
import com.example.e_ticaretapp.models.Product
import com.example.e_ticaretapp.ui.cart.CartViewModel.Companion.cartProducts

class CartFragment : Fragment() {

    companion object {
        fun newInstance() = CartFragment()
    }

    lateinit var productsAdapter: ProductsAdapter
    lateinit var productList: MutableList<Product>

    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: CartViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        val view = binding.root

        productList = mutableListOf()
        productsAdapter = ProductsAdapter(requireActivity(), productList)
        binding.listCart.adapter = productsAdapter

        binding.listCart.setOnItemLongClickListener { parent, view, position, id ->
            val builder = AlertDialog.Builder(requireContext())
            val removeView = layoutInflater.inflate(R.layout.dialog_remove, null)
            builder.setView(removeView)
            val dialog = builder.create()

            removeView.findViewById<Button>(R.id.btnYes).setOnClickListener {
                viewModel.removeProduct(position)
                viewModel.getCartProducts()
                dialog.dismiss()
            }

            removeView.findViewById<Button>(R.id.btnCancel).setOnClickListener {
                dialog.dismiss()
            }
            if (dialog.window != null){
                dialog.window!!.setBackgroundDrawable(ColorDrawable(0))
            }
            dialog.show()
            true
        }

        binding.listCart.setOnItemClickListener { parent, view, position, id ->
            MainActivity.selectedProduct = cartProducts[position]
            val navController = findNavController()
            navController.navigate(R.id.navigation_details)
        }

        binding.btnOrderNow.setOnClickListener {
            viewModel.orderNow()
            viewModel.emptyCart()
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(CartViewModel::class.java)
        subscribe()
        viewModel.getCartProducts()
    }

    private fun subscribe(){
        viewModel.productList.observe(viewLifecycleOwner) { pList ->
            productsAdapter.updateList(pList as MutableList<Product>)
            productList.clear()
            productList.addAll(pList)
        }
    }

}