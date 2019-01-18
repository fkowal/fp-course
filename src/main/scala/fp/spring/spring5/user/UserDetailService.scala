package fp.spring.spring5.user

import reactor.core.publisher.Mono

trait UserDetailService {
  def getUserDetails(user: User): Mono[UserDetails]
}
