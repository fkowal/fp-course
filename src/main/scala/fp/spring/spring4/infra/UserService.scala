package fp.spring.spring4.infra

import fp.spring.spring4.user.{User, UserDetailService, UserDetails}
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UserService extends UserDetailService {
  override def getUserDetails(user: User): UserDetails =
    UserDetails(userId = user.userId, details = user.toString)
}
