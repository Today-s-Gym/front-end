package umc.standard.todaygym.data.model

data class TabNewData(
    val code: Int,
    val isSuccess: Boolean,
    val message: String,
    val result: Result
) {
    data class Result(
        val content: List<Content>,
        val empty: Boolean,
        val first: Boolean,
        val last: Boolean,
        val number: Int,
        val numberOfElements: Int,
        val pageable: Pageable,
        val size: Int,
        val sort: SortX,
        val totalElements: Int,
        val totalPages: Int
    ) {
        data class Pageable(
            val offset: Int,
            val pageNumber: Int,
            val pageSize: Int,
            val paged: Boolean,
            val sort: SortX,
            val unpaged: Boolean
        )

        data class SortX(
            val empty: Boolean,
            val sorted: Boolean,
            val unsorted: Boolean
        )

        data class Content(
            val recordId:Int,
            val content: String,
            val createdTime: String,
            val imgUrl: String
        )
    }
}