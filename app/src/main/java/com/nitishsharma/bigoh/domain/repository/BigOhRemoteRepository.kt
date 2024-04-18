package com.nitishsharma.bigoh.domain.repository

import com.nitishsharma.bigoh.data.models.ImageListItem
import com.nitishsharma.bigoh.utils.utils.result.Result
import retrofit2.Retrofit
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

class BigOhRemoteRepository @Inject constructor(
    retrofit: Retrofit
) {
    val apiService = retrofit.create(BigOhApiService::class.java)

    suspend fun getImageList(page: Int): Result<ArrayList<ImageListItem>> {
        return try {
            val response = apiService.getImageList(page)
            Result.Success(response)
        } catch (e: Exception) {
            Result.Error(e)
        }
    }


    interface BigOhApiService {
        @GET("/v2/list")
        suspend fun getImageList(
            @Query("page") page: Int,
        ): ArrayList<ImageListItem>
    }
}