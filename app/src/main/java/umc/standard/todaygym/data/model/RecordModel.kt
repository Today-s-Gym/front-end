package umc.standard.todaygym.data.model

import com.google.gson.annotations.SerializedName


// 월 단위 캘린더 조회
data class RecordByMonth(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: List<Result>
)

// 일 단위 캘린더 조회
data class RecordByDay(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Result
)

// 최근 사용 태그 조회
data class RecentTag(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Result2
)

// 기록 추가 및 수정
data class RecordResponse(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: Int
)

data class RecordRequest (
    @SerializedName("content") val content: String,
    @SerializedName("tags") val tags: List<Tag>
)

// 기록 삭제
data class DeleteRecord(
    @SerializedName("isSuccess") val isSuccess: Boolean,
    @SerializedName("code") val code: Int,
    @SerializedName("message") val message: String,
    @SerializedName("result") val result: String
)

data class Result(
    @SerializedName("content") val content: String,
    @SerializedName("createdTime") val createdTime: String,
    @SerializedName("userName") val userName: String,
    @SerializedName("recordPhotos") val recordPhotos: List<Image>,
    @SerializedName("tags") val tags: List<Tag>
)

data class Result2(
    @SerializedName("content") val content: List<Tag2>
)
data class Image(
    @SerializedName("img_url") val img_url: String
)

data class Tag(
    @SerializedName("name") val name: String
)
data class Tag2(
    @SerializedName("tag") val tag: String
)
