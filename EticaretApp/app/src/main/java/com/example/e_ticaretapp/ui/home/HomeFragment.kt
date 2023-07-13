package com.example.e_ticaretapp.ui.home

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import com.example.e_ticaretapp.DetailsActivity
import com.example.e_ticaretapp.MainActivity
import com.example.e_ticaretapp.adapters.ProductsAdapter
import com.example.e_ticaretapp.databinding.FragmentHomeBinding
import com.example.e_ticaretapp.models.Product

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
        var selectedCategory = "All"
    }

    lateinit var productsAdapter: ProductsAdapter
    var categories: List<String> = listOf()
    lateinit var categoriesPopupMenu: PopupMenu
    lateinit var productList: MutableList<Product>

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root

        productList = mutableListOf()
        productsAdapter = ProductsAdapter(requireActivity(), productList)
        binding.listProducts.adapter = productsAdapter

        categoriesPopupMenu = PopupMenu(requireContext(), binding.btnCategory, Gravity.BOTTOM)
        categoriesPopupMenu.menu.add(0, 0, 0, "All")

        binding.btnCategory.setOnClickListener {
            categoriesPopupMenu.show()
            categoriesPopupMenu.setOnMenuItemClickListener {
                if (it.itemId == 0){
                    selectedCategory = "all"
                    viewModel.getAllProducts()
                }else{
                    selectedCategory = categories[it.itemId-1]
                    viewModel.getProductsByCategory(selectedCategory)
                }
                binding.btnCategory.setText(selectedCategory.replace("-"," ").split(" ").joinToString(separator = " ") { it.replaceFirstChar { it.uppercase() } })
                false
            }
        }

        binding.btnSearch.setOnClickListener {
            if(binding.txtSearch.text.toString() != ""){
                viewModel.getSearchedProducts(binding.txtSearch.text.toString())
            }else{
                viewModel.getAllProducts()
            }
        }

        binding.listProducts.setOnItemClickListener { parent, view, position, id ->
            MainActivity.selectedProduct = productList[position]
            val i = Intent(requireActivity(), DetailsActivity::class.java)
            startActivity(i)
        }

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        subscribe()

        viewModel.getAllProducts()
        viewModel.getCategories()
    }

    private fun subscribe() {
        viewModel.productList.observe(viewLifecycleOwner) { pList ->
            productsAdapter.updateList(pList as MutableList<Product>)
            productList.clear()
            productList.addAll(pList)
        }
        
        viewModel.categoriesList.observe(viewLifecycleOwner){ categoriesList ->
            categories = categoriesList
            for (item in categories){
                categoriesPopupMenu.menu.add(0,categoriesPopupMenu.menu.size(), 0,
                    item.replace("-"," ").split(" ").joinToString(separator = " ") { it.replaceFirstChar { it.uppercase() } }
                )
            }
        }
    }

}