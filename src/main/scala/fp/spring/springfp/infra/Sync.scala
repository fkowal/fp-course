package fp.spring.springfp.infra

import cats.{~>, Id, Monad}
import fp.spring.springfp.user._
import reactor.core.publisher.Mono

object Sync {
  implicit val idMonoNat = new ~>[Id, Mono] {
    override def apply[A](fa: Id[A]): Mono[A] = Mono.just(fa)
  }

  implicit val idMonad = Monad[Id]

  def controller: UserController[Id] = new UserController(repo, userService)

  object repo extends UserRepository[Id] {
    val users = scala.collection.mutable.Map[String, User]()

    override def getUserById(userId: String): Id[User] =
      users(userId)

    override def save(user: User): Id[String] = {
      users.put(user.userId, user)
      user.userId
    }
  }

  object userService extends UserDetailService[Id] {
    override def getUserDetails(user: User): Id[UserDetails] =
      if (user.name.contains("error"))
        throw new RuntimeException(s"failed fetching user details $user")
      else
        UserDetails(user.userId, details = "SyncImpl " + user.toString)
  }
}
