package fp.spring.spring4.user

//interface JavaUserRepository {
//  User getUserById(String userId)
//}

trait UserRepository {
  def getUserById(userId: String): User

  def save(user: User): Unit
}
