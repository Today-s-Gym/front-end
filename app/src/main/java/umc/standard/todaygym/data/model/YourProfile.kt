package umc.standard.todaygym.data.model

data class YourProfile(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Result
) {
    data class Result(
        val avatarImgUrl: String,
        val categoryName: String,
        val introduce: String,
        val locked: Boolean,
        val nickname: String,
        val userRecordCount: UserRecordCount
    )

    data class UserRecordCount(
        val cumulativeCount: Int,
        val remainUpgradeCount: Int,
        val thisMonthRecord: Int
    )
}