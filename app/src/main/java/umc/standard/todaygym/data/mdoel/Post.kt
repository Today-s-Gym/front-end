package umc.standard.todaygym.data.mdoel

import com.google.gson.annotations.SerializedName

data class RequestAddPost(
    @SerializedName("categoryId") var categoryId: Int,
    @SerializedName("content") var content: String,
    @SerializedName("postPhotos") var postPhotos: List<String>,
    @SerializedName("title") var title: String
)

data class Response(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: String
)