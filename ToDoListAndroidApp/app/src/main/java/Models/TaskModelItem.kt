package Models

data class TaskModelItem(
        var content: String,
        val createdDate: String,
        val taskId: String?,
        val user: Any,
        val userId: String
)