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
            @SerializedName("writerId") val writerId: Int,
            @SerializedName("writerAvatarImgUrl") val writerAvatarImgUrl: String,
            @SerializedName("writerNickName") val writerNickName: String
        )



    }
}

data class ChatData(
    @SerializedName("code")val code: Int,
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("message")val message: String,
    @SerializedName("result")val result: List<Result>
) {
    data class Result(
        @SerializedName("commentId") val commentId: Int,
        @SerializedName("content") val content: String,
        @SerializedName("mine") val mine: Boolean,
        @SerializedName("writerAvatarImgUrl") val writerAvatarImgUrl: String,
        @SerializedName("writerName") val writerName: String
    )
}

data class Report(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code")val code: Int,
    @SerializedName("message")val message: String,
    @SerializedName("result")val result: Int
)


data class AddChat(
    @SerializedName("postId") var postId: Int,
    @SerializedName("content") var content: String
)

data class RequestAddPost(
    @SerializedName("categoryId") var categoryId: Int,
    @SerializedName("title") var title: String,
    @SerializedName("content") var content: String,
    @SerializedName("recordId") var recordId: Int?

)

data class EditPost(
    val categoryId: Int,
    val title: String,
    val content: String,
    val postPhotos: List<String>,
    val recordId: Int?
)

data class Lock(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Result
) {
    data class Result(
        val locked: Boolean
    )
}
