package umc.standard.todaygym.data.model

import com.google.gson.annotations.SerializedName

data class PostData(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code")val code: Int,
    @SerializedName("message")val message: String,
    @SerializedName("result") val result: Result
){
    data class Result(
        @SerializedName("getPostRes") val getPostRes: GetPostRes,
        @SerializedName("mine") val mine: Boolean
    ) {
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



    }
}

data class ChatData(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: List<Result>
) {
    data class Result(
        val commentId: Int,
        val content: String,
        val mine: Boolean,
        val writerAvatarImgUrl: String,
        val writerName: String
    )
}
