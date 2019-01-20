package fp.spring.Ex6.infra

import fp.spring.Ex6.user.{Result, User, UserRepository}
import org.springframework.stereotype.Repository

@Repository
class InMemoryRepository extends UserRepository[Result] {
  val users = scala.collection.mutable.Map[String, User]()

  override def getUserById(userId: String): Result[User] =
    Result(users(userId))

  override def save(user: User): Result[Option[User]] =
    Result(users.put(user.userId, user))
}
