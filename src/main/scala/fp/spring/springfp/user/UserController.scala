package fp.spring.springfp.user

import cats.{Applicative, Monad}
import cats.implicits._
import org.springframework.web.bind.annotation._

@RestController
class UserController[F[_]: Monad](
  userRepository: UserRepository[F],
  service: UserDetailService[F]
) {

  @GetMapping(value = Array("/user/{userId}"))
  def get(@PathVariable("userId") userId: String) =
    userRepository.getUserById(userId)

  @PostMapping(value = Array("/user"))
  def put(@RequestBody user: User) =
    userRepository.save(user)

  @GetMapping(value = Array("/user/{userId}/age"))
  def functorRequired(@PathVariable("userId") userId: String) =
    userRepository
      .getUserById(userId)
      .map(_.age) // functor (syntax/extension method) needed to support this

  @GetMapping(value = Array("/user/{userId}/base"))
  def applicativeRequired(@PathVariable("userId") userId: String): F[BaseUser] = {
    def getUserFromDbOrGuest(userId: String): F[BaseUser] = {
      if (userId == "guest")
        Applicative[F].pure(Guest) // support Applicative type class instance to handle the default/fallback case
      else
        userRepository
          .getUserById(userId)
          .map(user => user: BaseUser)
    }

    getUserFromDbOrGuest(userId)
  }

  @GetMapping(value = Array("/user/{userId}/details"))
  def monadRequired(@PathVariable("userId") userId: String): F[UserDetails] =
    for {
      user <- userRepository.getUserById(userId)
      details <- service.getUserDetails(user)
    } yield details
}
