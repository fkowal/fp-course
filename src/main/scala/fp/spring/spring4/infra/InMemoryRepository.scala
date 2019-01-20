package fp.spring.spring4.infra

import fp.spring.spring4.user.{User, UserRepository}
import org.springframework.stereotype.Repository

@Repository
class InMemoryRepository extends UserRepository {
  val users = scala.collection.mutable.Map[String, User]()

  override def getUserById(userId: String): User =
    users(userId)

  override def save(user: User): Option[User] =
    users.put(user.userId, user)
}
