package Models

data class TaskModelItem(
    val content: String,
    val createdDate: String,
    val taskId: String,
    val user: Any,
    val userId: String
)