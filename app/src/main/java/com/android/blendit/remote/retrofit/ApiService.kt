package com.android.blendit.remote.retrofit

import com.android.blendit.remote.response.AnalystResponse
import com.android.blendit.remote.response.CategoryResponse
import com.android.blendit.remote.response.CategoryTutorialResponse
import com.android.blendit.remote.response.FavoriteResponse
import com.android.blendit.remote.response.RecommendationResponse
import com.android.blendit.remote.response.ResponseListFavorite
import com.android.blendit.remote.response.ResponseListProduct
import com.android.blendit.remote.response.ResponseLogin
import com.android.blendit.remote.response.ResponseRegister
import com.android.blendit.remote.response.ResponseUploadProfilePicture
import com.android.blendit.remote.response.TutorialResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.DELETE
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
    @POST("upload-profile-picture")
    suspend fun uploadProfilePicture(
        @Header("Authorization") token: String,
        @Part profilePicture: MultipartBody.Part
    ): ResponseUploadProfilePicture

    @DELETE("delete-profile-picture")
    suspend fun deleteProfilePict(
        @Header("Authorization") token: String
    ): ResponseListFavorite

    @Multipart
    @POST("predict")
    suspend fun predict(
        @Header("Authorization") token: String,
        @Part image: MultipartBody.Part,
        @Part("skintone") skintone: RequestBody,
        @Part("undertone") undertone: RequestBody,
        @Part("skin_type") skinType: RequestBody
    ): AnalystResponse

    @GET("tutorial")
    suspend fun getTutorial(
        @Header("Authorization") token: String,
        @Query("shape") shape: String,
        @Query("skintone") skintone: String,
        @Query("undertone") undertone: String,
        @Query("skin_type") skinType: String
    ): TutorialResponse

    @GET("recommendation")
    suspend fun getRecommendation(
        @Header("Authorization") token: String,
        @Query("skintone") skintone: String,
        @Query("undertone") undertone: String,
        @Query("skin_type") skinType: String
    ): RecommendationResponse

    @FormUrlEncoded
    @POST("addfavorite")
    suspend fun addFavorite(
        @Header("Authorization") token: String,
        @Field("userId") userId: String,
        @Field("productId") productId: String
    ): FavoriteResponse

    @FormUrlEncoded
    @DELETE("removefavorite")
    suspend fun removeFavorite(
        @Header("Authorization") token: String,
        @Field("userId") userId: String,
        @Field("productId") productId: String
    ): Call<FavoriteResponse>

    @FormUrlEncoded
    @POST("addfavorite")
    suspend fun addFavoriteMona(
        @Header("Authorization") token: String,
        @Field("userId") userId: String,
        @Field("productId") productId: String
    ): FavoriteResponse

    @GET("category")
    suspend fun getCategory(
        @Header("Authorization") token: String
    ): CategoryResponse

    @GET("categorytutorial")
    suspend fun getCategoryTutorial(
        @Header("Authorization") token: String,
        @Query("id") id: String
    ): CategoryTutorialResponse






}