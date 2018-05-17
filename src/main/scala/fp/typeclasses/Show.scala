package fp.typeclasses

trait Show[A] {
  def show(value: A): String
}

object Show {
  // materializer
  def apply[A](implicit s: Show[A]): Show[A] = s
  //  def apply[A: Show](): Show[A] = implicitly[Show[A]]

  // constructor
  def pure[A](func: A => String): Show[A] = new Show[A] {
    def show(a: A) = func(a)
  }

  implicit val showInt: Show[Int] = ???

  implicit val showString: Show[String] = ???

  implicit val showBoolean: Show[Boolean] = ???

  implicit def list[A: Show]: Show[List[A]] = ???

  object Syntax {
    // val.show support
    implicit class ShowSyntax[T: Show](t: T) {
      def show(): String = Show[T].show(t)
    }
  }
}
