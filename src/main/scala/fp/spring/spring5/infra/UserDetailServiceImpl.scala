package fp.spring.spring5.infra
import java.time.Duration

import fp.spring.spring5.user.{User, UserDetailService, UserDetails}
import org.springframework.stereotype.Service
import reactor.core.publisher.Mono

@Service
class UserDetailServiceImpl extends UserDetailService {
  override def getUserDetails(user: User): Mono[UserDetails] =
    Mono
      .delay(Duration.ofSeconds(1))
      .flatMap(
        _ =>
          if (user.name.contains("error"))
            Mono.error[UserDetails](new RuntimeException(s"failed fetching user details $user"))
          else
            Mono.just(UserDetails(userId = user.userId, details = user.toString))
      )
}
