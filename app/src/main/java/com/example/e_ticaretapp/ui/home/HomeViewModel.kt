package com.example.e_ticaretapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_ticaretapp.api.ApiConfig
import com.example.e_ticaretapp.models.Product
import com.example.e_ticaretapp.models.ProductList
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeViewModel : ViewModel() {

    private val _productList = MutableLiveData<List<Product>>()
    val productList: LiveData<List<Product>> get() = _productList

    private val _categories = MutableLiveData<List<String>>()
    val categoriesList: LiveData<List<String>> get() = _categories

    fun getProductsByCategory(category: String){
        val client = ApiConfig.getApiService().getProductsByCategory(category)

        client.enqueue(object: Callback<ProductList>{
            override fun onResponse(call: Call<ProductList>, response: Response<ProductList>) {
                val responseBody = response.body()
                if (!response.isSuccessful || responseBody == null){
                    return
                }
                _productList.postValue(responseBody.productList as List<Product>)
            }

            override fun onFailure(call: Call<ProductList>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    fun getSearchedProducts(searchTerm: String){

        val client = ApiConfig.getApiService().getSearchedProducts(searchTerm)

        client.enqueue(object: Callback<ProductList>{
            override fun onResponse(call: Call<ProductList>, response: Response<ProductList>) {
                val responseBody = response.body()
                if (!response.isSuccessful || responseBody == null){
                    return
                }
                _productList.postValue(responseBody.productList as List<Product>)
            }

            override fun onFailure(call: Call<ProductList>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    fun getCategories(){

        val client = ApiConfig.getApiService().getCategories()

        client.enqueue(object: Callback<List<String>>{
            override fun onResponse(call: Call<List<String>>, response: Response<List<String>>) {
                val responseBody = response.body()
                if(!response.isSuccessful || responseBody == null){
                    return
                }
                _categories.postValue(responseBody as List<String>)
            }

            override fun onFailure(call: Call<List<String>>, t: Throwable) {
                t.printStackTrace()
            }
        })
    }

    fun getAllProducts(){

        val client = ApiConfig.getApiService().getAllProducts()

        client.enqueue(object: Callback<ProductList>{
            override fun onResponse(call: Call<ProductList>, response: Response<ProductList>) {
                val responseBody = response.body()
                if (!response.isSuccessful || responseBody == null){
                    return
                }
                _productList.postValue(responseBody.productList as List<Product>)
            }

            override fun onFailure(call: Call<ProductList>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }

    fun getFirstProductsByLimit(limit: Int){

        val client = ApiConfig.getApiService().getFirstByLimit(limit)

        client.enqueue(object: Callback<ProductList>{
            override fun onResponse(call: Call<ProductList>, response: Response<ProductList>) {
                val responseBody = response.body()
                if (!response.isSuccessful || responseBody == null){
                    return
                }
                _productList.postValue(responseBody.productList as List<Product>)
            }

            override fun onFailure(call: Call<ProductList>, t: Throwable) {
                t.printStackTrace()
            }

        })
    }
}