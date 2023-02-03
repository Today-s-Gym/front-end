package umc.standard.todaygym.data.model

import com.google.gson.annotations.SerializedName

data class PostModel(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code")val code: Int,
    @SerializedName("message")val message: String,
    @SerializedName("result") val result: ResultPost
)

data class ResultPost(
    @SerializedName("getPostRes") val getPostRes: GetPostRes,
    @SerializedName("mine") val mine: Boolean
)

data class GetPostRes(
    @SerializedName("categoryName") val categoryName: String,
    @SerializedName("commentCounts") val commentCounts: Int,
    @SerializedName("content") val content: String,
    @SerializedName("createdAt") val createdAt: String,
    @SerializedName("likeCounts") val likeCounts: Int,
    @SerializedName("liked") val liked: Boolean,
    @SerializedName("postId") val postId: Int,
    @SerializedName("postPhotoList") val postPhotoList: List<String>,
    @SerializedName("recordContent") val recordContent: String,
    @SerializedName("recordCreatedAt") val recordCreatedAt: String,
    @SerializedName("recordId") val recordId: Int,
    @SerializedName("recordPhotoImgUrl") val recordPhotoImgUrl: String,
    @SerializedName("title") val title: String,
    @SerializedName("writerAvatarImgUrl") val writerAvatarImgUrl: String,
    @SerializedName("writerNickName") val writerNickName: String
)

