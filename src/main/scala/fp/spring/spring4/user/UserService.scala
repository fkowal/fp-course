package fp.spring.spring4.user

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UserService(
  userRepository: UserRepository,
  userDetailService: UserDetailService
) {

  def getUserById(userId: String): User =
    userRepository.getUserById(userId)

  def save(user: User): Option[User] =
    userRepository.save(user)

  def getUserAge(userId: String): Int = {
    val user = userRepository.getUserById(userId)

    user.age
  }

  def getUserDetails(userId: String): UserDetails = {
    val user = userRepository.getUserById(userId)

    userDetailService.getUserDetails(user)
  }
}
