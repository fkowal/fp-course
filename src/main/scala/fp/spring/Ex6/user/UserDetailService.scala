package fp.spring.Ex6.user

trait UserDetailService[F[_]] {
  def getUserDetails(user: User): F[UserDetails]
}
