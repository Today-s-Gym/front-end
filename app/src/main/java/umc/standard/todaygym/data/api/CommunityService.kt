package umc.standard.todaygym.data.api


import retrofit2.Call
import retrofit2.http.*
import umc.standard.todaygym.data.model.*

interface CommunityService {
    @GET("posts/{categoryId}")
    fun getBoard(
        @Header("Authorization") Authorization: String,
        @Path("categoryId") categoryId : Int
    ): Call<List<BoardData>>

    @GET("posts/{postId}")
    fun getPost(
        @Header("Authorization") Authorization: String,
        @Path("postId") postId : Int
    ): Call<List<PostModel>>

    @POST("post")
    fun addPost(
        @Header("Authorization") Authorization: String,
        @Body requestAddPost: RequestAddPost
    ) :Call<RequestAddPost>

    @POST("post")
    fun responseAddPost(
        @Header("Authorization") Authorization: String,
        @Body responseAddPost: Response
    ) :Call<Response>


}