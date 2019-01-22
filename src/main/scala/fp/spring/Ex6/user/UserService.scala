package fp.spring.Ex6.user

import org.springframework.stereotype.Service

@Service
class UserService(
  userRepository: UserRepository,
  userDetailService: UserDetailService
) {

  def getUserById(userId: String): Result[User] = ???

  def save(user: User): Result[Option[User]] = ???

  def getUserAge(userId: String): Result[Int] = ???

  def getUserDetails(userId: String): Result[UserDetails] = ???
}
