package Models

data class UserBody (
    val userId: String,
    val userName: String,
    val password: String,
    val email: String
)

data class SignUpUser(
    val userName: String,
    val password: String,
    val email: String
)