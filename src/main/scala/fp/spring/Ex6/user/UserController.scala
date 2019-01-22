package fp.spring.Ex6.user

import org.springframework.web.bind.annotation._

@RestController
class UserController(
  userService: UserService
) {

  @GetMapping(value = Array("/user/{userId}"))
  def get(@PathVariable("userId") userId: String): Result[User] =
    userService.getUserById(userId)

  @PostMapping(value = Array("/user"))
  def put(@RequestBody user: User): Result[Option[User]] =
    userService.save(user)

  @GetMapping(value = Array("/user/{userId}/age"))
  def age(@PathVariable("userId") userId: String): Result[Int] =
    userService.getUserAge(userId)

  @GetMapping(value = Array("/user/{userId}/details"))
  def userDetails(@PathVariable("userId") userId: String): Result[UserDetails] =
    userService.getUserDetails(userId)
}
