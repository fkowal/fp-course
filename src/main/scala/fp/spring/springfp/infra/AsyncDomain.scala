package fp.spring.springfp.infra

import java.time.Duration

import cats.arrow.FunctionK
import cats.{~>, Monad}
import fp.spring.springfp.user._
import reactor.core.publisher.Mono

object AsyncDomain {
  implicit val idMonoNat: ~>[Mono, Mono] = FunctionK.id

  implicit val monadMono = new Monad[Mono] {
    override def map[A, B](fa: Mono[A])(f: A => B): Mono[B] = fa.map(a => f(a))

    override def flatMap[A, B](fa: Mono[A])(f: A => Mono[B]): Mono[B] = fa.flatMap(a => f(a))

    override def tailRecM[A, B](a: A)(f: A => Mono[Either[A, B]]): Mono[B] =
      f(a).map(e => e.right.get)

    override def pure[A](x: A): Mono[A] = Mono.just(x)
  }

  def controller: UserController[Mono] =
    new UserController[Mono](new UserService(repo, UserDetailServiceImpl))

  object repo extends UserRepository[Mono] {
    val repo = Sync.repo

    override def getUserById(userId: String): Mono[User] =
      Mono.just(repo.getUserById(userId))

    override def save(user: User): Mono[Option[User]] =
      Mono.just(repo.save(user))
  }

  object UserDetailServiceImpl extends UserDetailService[Mono] {
    override def getUserDetails(user: User): Mono[UserDetails] =
      Mono
        .delay(Duration.ofSeconds(1))
        .flatMap(
          _ =>
            if (user.name.contains("error"))
              Mono.error[UserDetails](new RuntimeException(s"failed fetching user details $user"))
            else
              Mono.just(UserDetails(userId = user.userId, details = "AsyncImpl " + user.toString))
        )
  }
}
