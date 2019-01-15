package fp.spring.spring4.user

import org.springframework.web.bind.annotation._

@RestController
class UserController(
  userRepository: UserRepository,
  userDetailService: UserDetailService
) {

  @GetMapping(value = Array("/user/{userId}"))
  def get(@PathVariable("userId") userId: String): User =
    userRepository.getUserById(userId)

  @PostMapping(value = Array("/user"))
  def put(@RequestBody user: User): Unit =
    userRepository.save(user)

  @GetMapping(value = Array("/user/{userId}/age"))
  def age(@PathVariable("userId") userId: String): Int =
    userRepository
      .getUserById(userId)
      .age

  @GetMapping(value = Array("/user/{userId}/details"))
  def userDetails(@PathVariable("userId") userId: String): UserDetails = {
    val user = userRepository.getUserById(userId)

    userDetailService.getUserDetails(user)
  }
}
