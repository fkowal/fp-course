package fp.spring.Ex6.user

trait UserRepository {
  def getUserById(userId: String): Result[User]

  def save(user: User): Result[Option[User]]
}
