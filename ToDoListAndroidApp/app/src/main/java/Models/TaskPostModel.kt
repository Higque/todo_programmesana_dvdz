package Models

data class TaskPostModel(
        var content: String,
        val createdDate: String,
        val userId: String
)