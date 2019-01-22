package fp.spring.springfp.user

import scala.language.higherKinds

trait UserRepository[F[_]] {
  type UserId = String

  def getUserById(userId: UserId): F[User]

  def save(user: User): F[Option[User]]
}
