package umc.standard.todaygym.data.mdoel

data class PostModel(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Result
)
data class Result(
    val getPostRes: GetPostRes,
    val mine: Boolean
    )

data class GetPostRes(
        val categoryName: String,
        val commentCounts: Int,
        val content: String,
        val createdAt: String,
        val likeCounts: Int,
        val liked: Boolean,
        val postId: Int,
        val postPhotoList: List<String>,
        val recordContent: String,
        val recordCreatedAt: String,
        val recordId: Int,
        val recordPhotoImgUrl: String,
        val title: String,
        val writerAvatarImgUrl: String,
        val writerNickName: String)

