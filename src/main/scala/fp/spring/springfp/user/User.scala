package fp.spring.springfp.user

sealed trait BaseUser {
  def name: String
}
case class User(userId: String, name: String, age: Int) extends BaseUser
case object Guest extends BaseUser {
  val name = "gosc"
}
