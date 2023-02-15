package umc.standard.todaygym.data.model

data class BoardData(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: List<Result>
) {
    data class Result(
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
        val writerId:Int,
        val writerAvatarImgUrl: String,
        val writerNickName: String
    )
}

data class Heart(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Result
) {
    data class Result(
        val postId: Int,
        val result: String,
        val status: Boolean,
        val userId: Int
    )
}

