package umc.standard.todaygym.data.api


import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import umc.standard.todaygym.data.mdoel.*

interface CommunityService {
    @GET("posts/{categoryId}")
    fun getBoard(
        @Path("categoryId") categoryId : Int
    ): Call<List<BoardData>>

    @GET("posts/{postId}")
    fun getPost(
        @Path("postId") postId : Int
    ): Call<List<PostModel>>

    @POST("post")
    fun addPost(
        @Body requestAddPost: RequestAddPost
    ) :Call<RequestAddPost>

    @POST("post")
    fun responseAddPost(
        @Body responseAddPost: Response
    ) :Call<Response>

    @POST("comment")
    fun
}