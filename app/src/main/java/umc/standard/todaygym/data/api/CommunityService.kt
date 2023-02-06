package umc.standard.todaygym.data.api


import okhttp3.MultipartBody
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

    @GET("post/{postId}")
    fun getPost(
        @Path("postId") postId : Int
    ): Call<PostData>

    @GET("comments/{postId}")
    fun getChat(
        @Path("postId") postId : Int
    ):Call<ChatData>

    @Multipart
    @POST("post")
    fun addPost(
        @Part("postPostReq") requestAddPost: RequestAddPost
    ) :Call<RequestAddPost>


    @GET("record/recent")
    fun tabNewRecord(
        @Query("page") page : Int
    ) :Call<TabNewData>

    @POST("post/like")
    fun heart(
        @Body postId: Int
    ) :Call<Heart>


}