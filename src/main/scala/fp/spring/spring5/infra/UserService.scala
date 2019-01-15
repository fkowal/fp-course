package fp.spring.spring5.infra
import fp.spring.spring5.user.{User, UserDetailService, UserDetails}
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UserService extends UserDetailService {
  override def getUserDetails(user: User): Mono[UserDetails] =
    if (user.name.contains("error"))
      Mono.error(new RuntimeException(s"failed fetching user details $user"))
    else
      Mono.just(UserDetails(userId = user.userId, details = user.toString))
}
