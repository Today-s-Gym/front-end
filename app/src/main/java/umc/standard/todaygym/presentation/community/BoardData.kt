package umc.standard.todaygym.presentation.community

import android.widget.ImageView

data class BoardData(
    val isSuccess: Boolean,
    val code: Int,
    val message: String,
    val result: Result
) {
    data class Result(
        val categoryName: String,
        val postId: Int,
        val title: String,
        val content: String,
        val postPhotoList: String,
        val createdAt: String,
        val likeCounts: Int,
        val liked: Boolean,
        val commentCounts: Int,
        val writerAvatarImgUrl:String,
        val writerNickName: String,
        val recordId: Int,
        val recordPhotoImgUrl: String,
        val recordCreateAd: String,
        val recordContent: String
    )
}

