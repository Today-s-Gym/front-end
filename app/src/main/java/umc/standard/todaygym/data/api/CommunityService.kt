package umc.standard.todaygym.data.api


import okhttp3.MultipartBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import umc.standard.todaygym.data.model.BoardData
import retrofit2.http.*
import umc.standard.todaygym.data.model.*
import retrofit2.http.Field as Field

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

    @FormUrlEncoded
    @POST("post/like")
    fun heart(
        @Field("postId") postId: Int
    ) :Call<Heart>


    @POST("report/post")
    fun reportPost(
        @Body reportedId:Int
    ) :Call<Report>


    @POST("report/user")
    fun reportUser(
        @Body reportedId:Int
    ) :Call<Report>


    @POST("report/comment")
    fun reportChat(
        @Body reportedId:Int
    ) :Call<Report>

    @POST("comment")
    fun addChat(
        @Body addChat: AddChat
    ) :Call<AddChat>

    @FormUrlEncoded
    @PATCH("comment/delete")
    fun deleteChat(
        @Field("commentId") commentId: Int
    ) :Call<Report>

    @FormUrlEncoded
    @PATCH("post/delete")
    fun deletePost(
        @Field("postId") postId: Int
    ) :Call<Report>

    @FormUrlEncoded
    @Multipart
    @PATCH("post")
    fun editPost(
        @Field("postId") postId: Int,
        @Part("postPostReq") editPost: EditPost
    ) :Call<EditPost>


}