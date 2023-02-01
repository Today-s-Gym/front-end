package umc.standard.todaygym.data.api

import okhttp3.MultipartBody
import org.jetbrains.annotations.PropertyKey
import retrofit2.Call
import retrofit2.http.*
import umc.standard.todaygym.data.mdoel.*
import umc.standard.todaygym.data.mdoel.Tag

interface RecordInterface {
    @GET("record/month")
    fun getRecordByMonth(
        @Header("Authorization") Authorization: String,
        @Query("month") month: String
    ): Call<RecordByMonth>

    @GET("record/day")
    fun searchRecordByDay(
        @Header("Authorization") Authorization: String,
        @Query("date") date: String
    ): Call<RecordByDay>

    @GET("tag/recent")
    fun getRecentTag(
        @Header("Authorization") Authorization: String,
        @Query("page") page: Int
    ) : Call<RecentTag>

    @Multipart
    @POST("record")
    fun addRecord1(
        @Header("Authorization") Authorization: String,
        @Part image: MultipartBody.Part?,
        @Part ("recordGetReq") recordGetReq: AddRecordRequest1
    ) : Call<AddRecordResponse>

    @Multipart
    @POST("record")
    fun addRecord2(
        @Header("Authorization") Authorization: String,
        @Part ("recordGetReq") recordGetReq: AddRecordRequest1
    ) : Call<AddRecordResponse>

    @POST("record/delete")
    fun deleteRecord(
        @Header("Authorization") Authorization: String,
        @Query("date") date: String
    ) : Call<DeleteRecord>
}