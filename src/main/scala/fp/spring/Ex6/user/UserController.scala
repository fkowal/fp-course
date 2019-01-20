package fp.spring.Ex6.user

import org.springframework.web.bind.annotation._

@RestController
class UserController(
  userRepository: UserRepository,
  userDetailService: UserDetailService
) {

  @GetMapping(value = Array("/user/{userId}"))
  def get(@PathVariable("userId") userId: String): Result[User] =
    ???

  @PostMapping(value = Array("/user"))
  def put(@RequestBody user: User): Result[Option[User]] =
    ???

  @GetMapping(value = Array("/user/{userId}/age"))
  def age(@PathVariable("userId") userId: String): Result[Int] =
    ???

  @GetMapping(value = Array("/user/{userId}/details"))
  def userDetails(@PathVariable("userId") userId: String): Result[UserDetails] = {
    ???
  }
}
