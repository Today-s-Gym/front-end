package umc.standard.todaygym.data.api


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import umc.standard.todaygym.data.mdoel.BoardData

interface CommunityService {
    @GET("/posts/{categoryId}")
    fun getBoard(
        @Path("categoryId") categoryId : Int
    ): Call<List<BoardData>>
}