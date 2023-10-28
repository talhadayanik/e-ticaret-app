package com.example.e_ticaretapp.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.e_ticaretapp.api.ApiConfig
import com.example.e_ticaretapp.models.CartItem
import com.example.e_ticaretapp.models.OrderCallbackData
import com.example.e_ticaretapp.models.OrderItem
import com.example.e_ticaretapp.models.Product
import com.example.e_ticaretapp.models.ProductList
import com.example.e_ticaretapp.utils.Utils
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartViewModel : ViewModel() {

    companion object{
        val cartProducts: MutableList<Product> = mutableListOf()
        val cartItems : MutableList<CartItem> = mutableListOf()
        var orderItem : OrderItem? = null
        var orderedItems : OrderCallbackData? = null
    }

    private val _productList = MutableLiveData<List<Product>>()
    val productList: LiveData<List<Product>> get() = _productList

    fun emptyCart(){
        cartProducts.clear()
        _productList.postValue(cartProducts)
    }

    fun getCartProducts(){
        _productList.postValue(cartProducts)
    }

    fun removeProduct(id: Int){
        cartProducts.removeAt(id)
    }

    fun orderNow(){
        for (i in cartProducts){
            cartItems.add(CartItem(i.id))
        }
        orderItem = OrderItem(Utils.user!!.id, cartItems)

        if (orderItem != null){
            val client = ApiConfig.getApiService().orderItems(orderItem!!)

            client.enqueue(object: Callback<OrderCallbackData> {
                override fun onResponse(call: Call<OrderCallbackData>, response: Response<OrderCallbackData>) {
                    val responseBody = response.body()
                    if (!response.isSuccessful || responseBody == null){
                        return
                    }
                    //Notification
                }

                override fun onFailure(call: Call<OrderCallbackData>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }
    }
}