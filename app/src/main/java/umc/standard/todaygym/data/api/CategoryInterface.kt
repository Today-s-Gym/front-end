package umc.standard.todaygym.data.api

import retrofit2.Call
import retrofit2.http.GET
import umc.standard.todaygym.data.model.CategoryResponse

interface CategoryInterface {
    @GET("/categories")
    fun CategoryRequest(): Call<CategoryResponse>
}