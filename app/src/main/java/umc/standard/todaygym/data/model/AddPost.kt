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

