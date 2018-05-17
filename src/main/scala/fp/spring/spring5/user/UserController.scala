package fp.spring.spring5.user

import org.springframework.web.bind.annotation._
import reactor.core.publisher.Mono

@RestController
class UserController(userRepository: UserRepository) {

  @GetMapping(value = Array("/user/{userId}"))
  def get(@PathVariable("userId") userId: String): Mono[User] =
    userRepository.getUserById(userId)

  @PostMapping(value = Array("/user"))
  def put(@RequestBody user: User): Unit =
    userRepository.save(user)

  @GetMapping(value = Array("/user/{userId}/age"))
  def age(@PathVariable("userId") userId: String): Mono[Int] =
    userRepository
      .getUserById(userId)
      .map(_.age)
}
