package fp.spring.Ex6.user

import org.springframework.web.bind.annotation._

@RestController
class UserController(
  userRepository: UserRepository[Result],
  userDetailService: UserDetailService[Result]
) {

  @GetMapping(value = Array("/user/{userId}"))
  def get(@PathVariable("userId") userId: String): Result[User] =
    userRepository.getUserById(userId)

  @PostMapping(value = Array("/user"))
  def put(@RequestBody user: User): Result[Option[User]] =
    userRepository.save(user)

  @GetMapping(value = Array("/user/{userId}/age"))
  def age(@PathVariable("userId") userId: String): Result[Int] = {
    val userResult = userRepository.getUserById(userId)
    val age = userResult.result.age

    Result(age)
// or use Functor.map
//    userRepository.getUserById(userId)
//      .map(_.age)
  }

  @GetMapping(value = Array("/user/{userId}/details"))
  def userDetails(@PathVariable("userId") userId: String): Result[UserDetails] = {
    val userResult = userRepository.getUserById(userId)

    userDetailService.getUserDetails(userResult.result)

//    or using flatMap
//    userRepository
//      .getUserById(userId)
//      .flatMap(userDetailService.getUserDetails(_))

//    or using for comprehension
//    for {
//      user <- userRepository.getUserById(userId)
//      details <- userDetailService.getUserDetails(user)
//    } yield details
  }
}
