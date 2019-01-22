package fp.spring.springfp.user

import org.springframework.web.bind.annotation._

@RestController
class UserController[F[_]](
  userService: UserService[F]
) {

  @GetMapping(value = Array("/user/{userId}"))
  def get(@PathVariable("userId") userId: String): F[User] =
    userService.getUserById(userId)

  @PostMapping(value = Array("/user"))
  def post(@RequestBody user: User): F[Option[User]] =
    userService.save(user)

  @GetMapping(value = Array("/user/{userId}/age"))
  def getAge(@PathVariable("userId") userId: String): F[Int] =
    userService.getUserAge(userId)

  @GetMapping(value = Array("/user/{userId}/base"))
  def getBaseUser(@PathVariable("userId") userId: String): F[BaseUser] = {
    userService.getBaseUser(userId)
  }

  @GetMapping(value = Array("/user/{userId}/details"))
  def userDetails(@PathVariable("userId") userId: String): F[UserDetails] =
    userService.getUserDetails(userId)
}
