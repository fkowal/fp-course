package fp.spring.spring4

import org.springframework.stereotype.Repository

//interface UR {
//  User getUserById(String userId)
//}

trait UserRepository {
  def getUserById(userId: String): User

  def save(user: User): Unit
}

@Repository
class InMemoryRepository extends UserRepository {
  val users = scala.collection.mutable.Map[String, User]()

  override def getUserById(userId: String): User =
    users(userId)

  override def save(user: User): Unit = users.put(user.userId, user)
}
