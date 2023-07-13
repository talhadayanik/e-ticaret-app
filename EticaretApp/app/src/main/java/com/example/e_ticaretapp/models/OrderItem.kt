package com.example.e_ticaretapp.models

data class OrderItem(
    var userId: Long = 1,
    var products: Collection<CartItem> = listOf()
)
