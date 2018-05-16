package fp.spring.springfp

import cats.Id
import reactor.core.publisher.Mono

import scala.language.higherKinds

trait UserRepository[F[_]] {
  def getUserById(userId: String): F[User]

  def save(user: User): F[String]
}

class InMemoryRepository extends UserRepository[Id] {
  val users = scala.collection.mutable.Map[String, User]()

  override def getUserById(userId: String): Id[User] =
    users(userId)

  override def save(user: User): Id[String] = {
    users.put(user.id, user)
    user.id
  }
}

class MonoRepo extends UserRepository[Mono] {
  val repo = new InMemoryRepository()

  override def getUserById(userId: String): Mono[User] =
    Mono.just(repo.getUserById(userId))

  override def save(user: User): Mono[String] =
    Mono.just(repo.save(user))
}
