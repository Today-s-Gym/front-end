package umc.standard.todaygym.data.model
import com.google.gson.annotations.SerializedName

//singIn, signUp 관련 data 클래스
data class SignRequest(
    @SerializedName("email")val email:String,
    @SerializedName("password")val password:String
)
//signUp response data 클래스
data class SignUpResponse(
    @SerializedName("isSuccess")val isSuccess:Boolean,
    @SerializedName("code")val statusCode:Int,
    @SerializedName("message")val message:String,
    @SerializedName("result")val result: SignUpResponseResult,
)
data class SignUpResponseResult(
    @SerializedName("accessToken")val accessToken:String ,
    @SerializedName("refreshToken")val refreshToken:String
)
