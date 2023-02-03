package umc.standard.todaygym.data.model

import com.google.gson.annotations.SerializedName

data class CategoryResponse(
    @SerializedName("isSuccess") val isSuccess :Boolean,
    @SerializedName("code") val code :Int,
    @SerializedName("message") val msg :String,
    @SerializedName("result") val result :List<String>,

)
