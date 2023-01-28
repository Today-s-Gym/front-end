package umc.standard.todaygym.data.api

import com.google.gson.JsonElement
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import umc.standard.todaygym.data.mdoel.SignRequest
import umc.standard.todaygym.data.mdoel.SignUpResponse

interface UserInterface {
    @POST("/auth/signup")
    fun signUp(
        @Body body: SignRequest
    ): Call<SignUpResponse>

    @POST("/auth/login")
    fun logIn(@Body userSignUp: SignRequest): Call<JsonElement>

}
