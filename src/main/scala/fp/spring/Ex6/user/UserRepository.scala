package fp.spring.Ex6.user

trait UserRepository[F[_]] {
  def getUserById(userId: String): F[User]

  def save(user: User): F[Option[User]]
}
