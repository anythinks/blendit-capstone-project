package com.android.blendit.remote.retrofit

import com.android.blendit.remote.response.AnalystResponse
import com.android.blendit.remote.response.ResponseListFavorite
import com.android.blendit.remote.response.ResponseListProduct
import com.android.blendit.remote.response.ResponseLogin
import com.android.blendit.remote.response.ResponseRegister
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Query

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    suspend fun register(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): ResponseRegister

    @FormUrlEncoded
    @POST("login")
    suspend fun login(
        @Field("email") email: String,
        @Field("password") password: String
    ): ResponseLogin

    @GET("listproduct")
    suspend fun listProduct(
        @Header("Authorization") token: String,
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Response<ResponseListProduct>

    @GET("listfavorite")
    suspend fun listFavorite(
        @Header("Authorization") token: String,
        @Query("userId") userId: String
    ): Response<ResponseListFavorite>

    @Multipart
    @POST("predict")
    suspend fun predict(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part,
        @Part("skintone") skintone: RequestBody,
        @Part("undertone") undertone: RequestBody,
        @Part("skin_type") skin_type: RequestBody,
    ): AnalystResponse


}