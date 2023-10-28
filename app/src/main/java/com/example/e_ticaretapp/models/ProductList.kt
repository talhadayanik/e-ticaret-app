package com.example.e_ticaretapp.models

import com.google.gson.annotations.SerializedName

data class ProductList(
    @SerializedName("products")
    var productList: List<Product?>? = null
)
