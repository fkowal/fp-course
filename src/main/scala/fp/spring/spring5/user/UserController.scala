package fp.spring.spring5.user

import org.springframework.web.bind.annotation._
import reactor.core.publisher.Mono

@RestController
class UserController(
  userService: UserService
) {

  @GetMapping(value = Array("/user/{userId}"))
  def get(@PathVariable("userId") userId: String): Mono[User] =
    userService.getUserById(userId)

  @PostMapping(value = Array("/user"))
  def post(@RequestBody user: User): Mono[Option[User]] =
    userService.save(user)

  @GetMapping(value = Array("/user/{userId}/age"))
  def age(@PathVariable("userId") userId: String): Mono[Int] =
    userService
      .getUserById(userId)
      .map(_.age)

  @GetMapping(value = Array("/user/{userId}/details"))
  def userDetails(@PathVariable("userId") userId: String): Mono[UserDetails] =
    userService.getUserDetails(userId)
}
