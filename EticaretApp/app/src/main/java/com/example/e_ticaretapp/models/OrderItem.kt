package com.example.e_ticaretapp.models

data class OrderItem(
    var userId: Long? = null,
    var products: Collection<CartItem> = listOf()
)
