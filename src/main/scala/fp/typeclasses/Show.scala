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

  implicit val showInt: Show[Int] = pure(_.toString)

  implicit val showString: Show[String] = pure(s => s)

  implicit val showBoolean: Show[Boolean] = pure(if (_) "on" else "off")

  implicit def list[A: Show]: Show[List[A]] =
    pure(list => "[" + list.map(a => implicitly[Show[A]].show(a)).mkString(",") + "]")

  object Syntax {
    // val.show support
    implicit class ShowSyntax[T: Show](t: T) {
      def show(): String = Show[T].show(t)
    }
  }
}
