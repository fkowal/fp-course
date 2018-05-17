package fp.spring.spring5

import org.springframework.web.bind.annotation._

@RestController
class UserController(userRepository: UserRepository) {

  @GetMapping(value = Array("/user/{userId}"))
  def get(@PathVariable("userId") userId: String) =
    userRepository.getUserById(userId)

  @PostMapping(value = Array("/user"))
  def put(@RequestBody user: User): Unit =
    userRepository.save(user)
}
