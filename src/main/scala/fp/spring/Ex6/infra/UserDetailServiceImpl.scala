package fp.spring.Ex6.infra

import fp.spring.Ex6.user._
import org.springframework.stereotype.Service

@Service
class UserService extends UserDetailService[Result] {
  override def getUserDetails(user: User): Result[UserDetails] =
    if (user.name.contains("error"))
      throw new RuntimeException(s"user with error $user")
    else
      Result(UserDetails(user.userId, details = user.toString))
}
