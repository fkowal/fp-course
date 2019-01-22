package fp.spring.springfp.user

import cats.Monad
import cats.implicits._
import org.springframework.stereotype.Service

@Service
class UserService[F[_]](
  userRepository: UserRepository[F],
  userDetailService: UserDetailService[F]
)(implicit val monad: Monad[F]) {

  def getUserById(userId: String): F[User] =
    userRepository.getUserById(userId)

  def save(user: User): F[Option[User]] =
    userRepository.save(user)

  def getUserAge(userId: String): F[Int] =
//    monad.map(getUserById(userId))(_.age)
    getUserById(userId)
      .map(_.age) // functor (syntax/extension method) needed to support this

  def getBaseUser(userId: String): F[BaseUser] =
    if (userId == "guest")
      monad.pure(Guest)
    else
      userRepository
        .getUserById(userId)
        .map(user => user: BaseUser)

  def getUserDetails(userId: String): F[UserDetails] =
//    for {
//      user <- userRepository.getUserById(userId)
//      details <- userDetailService.getUserDetails(user)
//    } yield details
    userRepository
      .getUserById(userId)
      .flatMap(userDetailService.getUserDetails)
}
