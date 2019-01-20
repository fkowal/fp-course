package fp.spring.Ex6.infra

import fp.spring.Ex6.user._
import org.springframework.stereotype.Service

@Service
class UserService extends UserDetailService {
  override def getUserDetails(user: User): Result[UserDetails] =
    ???
}
