package fp.spring.spring4.user

import org.springframework.web.bind.annotation._

@RestController
class UserController(
  userService: UserService
) {

  @GetMapping(value = Array("/user/{userId}"))
  def get(@PathVariable("userId") userId: String): User =
    userService.getUserById(userId)

  @PostMapping(value = Array("/user"))
  def post(@RequestBody user: User): Option[User] =
    userService.save(user)

  @GetMapping(value = Array("/user/{userId}/age"))
  def age(@PathVariable("userId") userId: String): Int =
    userService.getUserAge(userId)

  @GetMapping(value = Array("/user/{userId}/details"))
  def userDetails(@PathVariable("userId") userId: String): UserDetails =
    userService.getUserDetails(userId)
}
