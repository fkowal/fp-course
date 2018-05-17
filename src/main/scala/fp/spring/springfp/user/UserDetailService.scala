package fp.spring.springfp.user

trait UserDetailService[F[_]] {
  def getUserDetails(user: User): F[UserDetails]
}
