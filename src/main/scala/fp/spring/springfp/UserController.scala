package fp.spring.springfp

import cats.Functor
import cats.implicits._
import org.springframework.web.bind.annotation._

@RestController
class UserController[F[_]: Functor](userRepository: UserRepository[F]) {

  @GetMapping(value = Array("/user/{userId}"))
  def get(@PathVariable("userId") userId: String) =
    userRepository.getUserById(userId)

  @PostMapping(value = Array("/user"))
  def put(@RequestBody user: User) =
    userRepository.save(user)

  @GetMapping(value = Array("/user/{userId}/age"))
  def age(@PathVariable("userId") userId: String) =
    userRepository.getUserById(userId).map(_.age)
}
