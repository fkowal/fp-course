package fp.spring.Ex6.user

trait UserDetailService {
  def getUserDetails(user: User): Result[UserDetails]
}
