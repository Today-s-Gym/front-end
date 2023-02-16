package umc.standard.todaygym.data.api

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.*
import umc.standard.todaygym.data.model.AddSignResponse
import umc.standard.todaygym.data.model.MyPageResponse
import umc.standard.todaygym.data.model.SignRequest
import umc.standard.todaygym.data.model.SignUpResponse
import umc.standard.todaygym.data.model.*

interface UserInterface {
    @POST("/oauth/kakao")
    fun kakaoLogIn(@Query(value="token",encoded = true) kakaoToken:String ):Call<SignUpResponse>

    @POST("/auth/login")
    fun logIn(@Body userSignUp: SignRequest): Call<JsonElement>

    @GET("/login/nickname")
    fun nickNameCheck(@Query("nickname")nickname:String):Call<AddSignResponse>

    @PUT("/login/introduce")
    fun addLogIn(
        @Query("nickname")nickname:String,
        @Query("introduce")statusMsg:String):Call<AddSignResponse>

    @PUT("/login/sports")
    fun addSports(
        @Query("categoryid")categoryId:Int):Call<AddSignResponse>

    @GET("user/profile/{userId}")
    fun yourProfile(
        @Path("userId") userId : Int
    ): Call<YourProfile>

    @GET("/user/mypage")
    fun getMyPage():Call<MyPageResponse>


}
