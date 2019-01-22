package fp.spring.spring5.user

import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UserService(
  userRepository: UserRepository,
  userDetailService: UserDetailService
) {

  def getUserById(userId: String): Mono[User] =
    userRepository.getUserById(userId)

  def save(user: User): Mono[Option[User]] =
    userRepository.save(user)

  def getUserAge(userId: String): Mono[Int] =
    getUserById(userId)
      .map(_.age)

  def getUserDetails(userId: String): Mono[UserDetails] =
    for {
      user <- userRepository.getUserById(userId)
      details <- userDetailService.getUserDetails(user)
    } yield details
//    userRepository
//      .getUserById(userId)
//      .flatMap(userDetailService.getUserDetails)
}
