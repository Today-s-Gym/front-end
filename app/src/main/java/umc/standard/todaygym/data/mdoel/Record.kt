package umc.standard.todaygym.data.mdoel

data class Record(
    val year: Int,
    val month: Int,
    val day: Int,
    var content: String,
    var pictures: ArrayList<String>,
    var tags: ArrayList<String>
)
