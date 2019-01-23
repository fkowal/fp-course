package fp.typeclasses
import simulacrum.typeclass

@typeclass
trait Show[A] {
  def show(value: A): String
}

object Show {

  // constructor
  def pure[A](func: A => String): Show[A] = new Show[A] {
    def show(a: A) = func(a)
  }

  implicit val showInt: Show[Int] = ???

  implicit val showString: Show[String] = ???

  implicit val showBoolean: Show[Boolean] = ???

  implicit def list[A: Show]: Show[List[A]] = ???

}
