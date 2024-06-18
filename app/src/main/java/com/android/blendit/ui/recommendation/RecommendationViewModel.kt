package com.android.blendit.ui.recommendation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.blendit.preference.AccountPreference
import com.android.blendit.remote.response.FavoriteResponse
import com.android.blendit.remote.response.RecommendationResult
import com.android.blendit.remote.retrofit.ApiConfig
import com.android.blendit.viewmodel.Repository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecommendationViewModel(accountPreference: AccountPreference) : ViewModel() {

    private val repository = Repository(accountPreference)

    private val _recommendationResult = MutableLiveData<List<RecommendationResult>>()
    val recommendationResult: LiveData<List<RecommendationResult>> = _recommendationResult

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _favoriteResponse =
        MutableLiveData<com.android.blendit.remote.Result<FavoriteResponse>>()
    val favoriteResponse: LiveData<com.android.blendit.remote.Result<FavoriteResponse>> =
        _favoriteResponse

    fun getRecommendations(token: String, skintone: String, undertone: String, skinType: String) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val apiService = ApiConfig.getApiService()
                val response = apiService.getRecommendation(token, skintone, undertone, skinType)
                if (response.status == "error") {
                    _errorMessage.postValue("Failed to get recommendations: ${response.message}")
                } else {
                    _recommendationResult.postValue(response.recommendationResult)
                }
            } catch (e: Exception) {
                Log.e("RecommendationViewModel", "Exception during API call: ${e.message}", e)
                _errorMessage.postValue("Failed to get recommendations: ${e.message}")
            }
        }
    }

    fun addFavorite(userId: String, productId: String) {
        viewModelScope.launch(Dispatchers.IO) {
            _favoriteResponse.postValue(com.android.blendit.remote.Result.Loading)
            val result = repository.addFavorite(userId, productId)
            _favoriteResponse.postValue(result)
        }
    }

}


//class RecommendationViewModel : ViewModel() {
//
//    private val _recommendationResult = MutableLiveData<List<RecommendationResult>>()
//    val recommendationResult: LiveData<List<RecommendationResult>> = _recommendationResult
//
//    private val _errorMessage = MutableLiveData<String>()
//    val errorMessage: LiveData<String> = _errorMessage
//
//    fun getRecommendations(token: String, skintone: String, undertone: String, skinType: String) {
//        viewModelScope.launch(Dispatchers.IO) {
//            try {
//                val apiService = ApiConfig.getApiService()
//                val response = apiService.getRecommendation(token, skintone, undertone, skinType)
//                if (response.status == "error") {
//                    _errorMessage.postValue("Failed to get recommendations: ${response.message}")
//                } else {
//                    _recommendationResult.postValue(response.recommendationResult)
//                }
//            } catch (e: Exception) {
//                Log.e("RecommendationViewModel", "Exception during API call: ${e.message}", e)
//                _errorMessage.postValue("Failed to get recommendations: ${e.message}")
//            }
//        }
//    }
//}