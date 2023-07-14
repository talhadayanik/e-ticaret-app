package com.example.e_ticaretapp.services

import com.example.e_ticaretapp.models.JWTData
import com.example.e_ticaretapp.models.LoginData
import com.example.e_ticaretapp.models.OrderCallbackData
import com.example.e_ticaretapp.models.OrderItem
import com.example.e_ticaretapp.models.ProductList
import com.example.e_ticaretapp.models.UpdateData
import com.example.e_ticaretapp.models.UserInfo
import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.Url

interface ApiService {

    @POST("auth/login")
    fun login(@Body loginData: LoginData): Call<JWTData>

    @GET("products")
    fun getFirstByLimit(@Query("limit") limit:Int): Call<ProductList>

    @GET("products/search")
    fun getSearchedProducts(@Query("q") q: String) : Call<ProductList>

    @GET("products")
    fun getAllProducts(): Call<ProductList>

    @GET("products/categories")
    fun getCategories(): Call<List<String>>

    @POST("carts/add")
    fun orderItems(@Body orderItem: OrderItem) : Call<OrderCallbackData>

    @GET("products/category/{category}")
    fun getProductsByCategory(@Path("category") category: String): Call<ProductList>

    @GET("users/{userId}")
    fun getUserInfoById(@Path("userId") userId: String): Call<UserInfo>

    @Headers(value = ["Content-type: application/json"])
    @PUT("users/{userId}")
    fun updateUser(@Path("userId") userId: String, @Body updateData: UpdateData): Call<UserInfo>
}