package fp.spring.spring5.user

import reactor.core.publisher.Mono

trait UserRepository {
  def getUserById(userId: String): Mono[User]

  def save(user: User): Mono[Option[User]]
}
