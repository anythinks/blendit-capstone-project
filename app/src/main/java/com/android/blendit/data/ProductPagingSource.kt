package com.android.blendit.data

import androidx.paging.LoadState
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.android.blendit.preference.AccountPreference
import com.android.blendit.remote.response.ItemsProduct
import com.android.blendit.remote.retrofit.ApiService

class ProductPagingSource(private val pref: AccountPreference, private val apiService: ApiService) :
    PagingSource<Int, ItemsProduct>() {

    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, ItemsProduct> {
        return try {
            val position = params.key ?: INITIAL_PAGE_INDEX
            val token = pref.getLoginInfo().token.toString()
            if (token.isEmpty()) {
                LoadState.Error(Exception("Token is empty"))
            }
            val responseData = apiService.listProduct(token, position, params.loadSize)
            if (!responseData.isSuccessful) {
                LoadState.Error(Exception("Failed to load"))
            }
            LoadResult.Page(
                data = responseData.body()?.items ?: emptyList(),
                prevKey = if (position == INITIAL_PAGE_INDEX) null else position - 1,
                nextKey = if (responseData.body()?.items.isNullOrEmpty()) null else position + 1
            )
        } catch (exception: Exception) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, ItemsProduct>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}