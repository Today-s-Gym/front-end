package umc.standard.todaygym.data.api


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import umc.standard.todaygym.data.model.BoardData
import retrofit2.http.*
import umc.standard.todaygym.data.model.*

interface CommunityService {
    @GET("posts/{categoryId}")
    fun getBoard(
        @Path("categoryId") categoryId : Int
    ): Call<BoardData>

    @GET("posts/{postId}")
    fun getPost(
        @Path("postId") postId : Int
    ): Call<PostData>

    @GET("comments/{postId}")
    fun getChat(
        @Path("postId") postId : Int
    ):Call<ChatData>

    @POST("post")
    fun addPost(
        @Body requestAddPost: RequestAddPost
    ) :Call<RequestAddPost>

    @POST("post")
    fun responseAddPost(
        @Body responseAddPost: Response
    ) :Call<Response>


}