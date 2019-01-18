package fp.spring.spring4.user

import reactor.core.publisher.Mono

trait UserDetailService {
  def getUserDetails(user: User): UserDetails
}
