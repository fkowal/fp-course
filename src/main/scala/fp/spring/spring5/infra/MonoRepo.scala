package fp.spring.spring5.infra

import java.time.Duration

import fp.spring.spring5.user.{User, UserRepository}
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono

@Repository
class MonoRepo extends UserRepository {
  val users = scala.collection.mutable.Map[String, User]()

  override def getUserById(userId: String): Mono[User] =
    Mono
      .delay(Duration.ofSeconds(1))
      .flatMap(_ => Mono.just(users(userId)))

  override def save(user: User): Mono[Option[User]] =
    Mono
      .just(users.put(user.userId, user))
}
