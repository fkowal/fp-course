package fp.spring.spring4.infra

import fp.spring.spring4.user.{User, UserDetailService, UserDetails}
import org.springframework.stereotype.Service

@Service
class UserService extends UserDetailService {
  override def getUserDetails(user: User): UserDetails =
    if (user.name.contains("error"))
      throw new RuntimeException(s"failed fetching user details $user")
    else
      UserDetails(userId = user.userId, details = user.toString)
}
