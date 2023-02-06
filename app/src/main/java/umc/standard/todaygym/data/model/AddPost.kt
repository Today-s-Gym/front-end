package umc.standard.todaygym.data.model

import com.google.gson.annotations.SerializedName


data class RequestAddPost(
    @SerializedName("categoryId") var categoryId: Int,
    @SerializedName("title") var title: String,
    @SerializedName("content") var content: String

)


