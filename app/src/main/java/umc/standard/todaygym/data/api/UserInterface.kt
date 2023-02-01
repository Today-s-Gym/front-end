package umc.standard.todaygym.data.api

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query
import umc.standard.todaygym.data.model.SignRequest
import umc.standard.todaygym.data.model.SignUpResponse

interface UserInterface {
    @POST("/oauth/kakao")
    fun kakaoLogIn(@Query(value="code",encoded = true) kakaoToken:String ):Call<SignUpResponse>

    @POST("/auth/login")
    fun logIn(@Body userSignUp: SignRequest): Call<JsonElement>

}
