package umc.standard.todaygym.data.api

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*
import umc.standard.todaygym.data.model.*

interface RecordService {
    // 기록 조회(월별)
    @GET("record/month")
    fun getRecordByMonth(
        @Query("month") month: String
    ): Call<RecordByMonth>

    // 기록 조회(일별)
    @GET("record/day")
    fun searchRecordByDay(
        @Query("date") date: String
    ): Call<RecordByDay>

    // 최근 태그 조회
    @GET("tag/recent")
    fun getRecentTag(
        @Query("page") page: Int
    ) : Call<RecentTag>

    // 기록 추가(이미지 있을 때)
    @Multipart
    @POST("record")
    fun addRecord1(
        @Part image: List<MultipartBody.Part?>,
        @Part ("recordGetReq") recordGetReq: RecordRequest
    ) : Call<RecordResponse>

    // 기록 추가(이미지 없을 때)
    @Multipart
    @POST("record")
    fun addRecord2(
        @Part ("recordGetReq") recordGetReq: RecordRequest
    ) : Call<RecordResponse>

    // 기록 수정(이미지 있을 때)
    @Multipart
    @POST("record/update")
    fun modifyRecord1(
        @Query("date") date: String,
        @Part image: List<MultipartBody.Part?>,
        @Part ("recordGetReq") recordGetReq: RecordRequest
    ) : Call<RecordResponse>

    // 기록 수정(이미지 없을 때)
    @Multipart
    @POST("record/update")
    fun modifyRecord2(
        @Query("date") date: String,
        @Part ("recordGetReq") recordGetReq: RecordRequest
    ) : Call<RecordResponse>

    // 기록 삭제
    @POST("record/delete")
    fun deleteRecord(
        @Query("date") date: String
    ) : Call<DeleteRecord>
}