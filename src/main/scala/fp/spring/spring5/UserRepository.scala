package fp.spring.spring5

import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

trait UserRepository {
  def getUserById(userId: String): Mono[User]

  def save(user: User): Mono[Unit]
}

@Repository
class InMemoryRepository extends UserRepository {
  val users = scala.collection.mutable.Map[String, User]()

  override def getUserById(userId: String): Mono[User] =
    Mono.just(users(userId))

  override def save(user: User): Mono[Unit] =
    Mono.just(users.put(user.userId, user))
}

//class CompletableToOther extends ~>
