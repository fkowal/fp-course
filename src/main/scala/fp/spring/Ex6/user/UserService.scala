package fp.spring.Ex6.user

import org.springframework.stereotype.Service

@Service
class UserService(
  userRepository: UserRepository[Result],
  userDetailService: UserDetailService[Result]
) {

  def getUserById(userId: String): Result[User] =
    userRepository.getUserById(userId)

  def save(user: User): Result[Option[User]] =
    userRepository.save(user)

  def getUserAge(userId: String): Result[Int] = {
    val user = userRepository.getUserById(userId)

    Result(user.result.age)
  }

  def getUserDetails(userId: String): Result[UserDetails] = {
    val user = userRepository.getUserById(userId).result

    userDetailService.getUserDetails(user)
  }
}
