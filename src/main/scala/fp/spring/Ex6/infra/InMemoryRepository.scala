package fp.spring.Ex6.infra

import fp.spring.Ex6.user.{Result, User, UserRepository}
import org.springframework.stereotype.Repository

@Repository
class InMemoryRepository extends UserRepository {
  val users = scala.collection.mutable.Map[String, User]()

  override def getUserById(userId: String): Result[User] =
    ???

  override def save(user: User): Result[Option[User]] =
    ???
}
