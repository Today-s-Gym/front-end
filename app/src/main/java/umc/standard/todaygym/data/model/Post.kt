package umc.standard.todaygym.data.model

import com.google.gson.annotations.SerializedName

data class RequestAddPost(
    @SerializedName("image")var image: List<String>,
    @SerializedName("postPostReq")var postPostReq: PostPostReq
){
    data class PostPostReq(
        @SerializedName("categoryId") var categoryId: Int,
        @SerializedName("content") var content: String,
        @SerializedName("title") var title: String
    )
}

data class Response(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: String
)