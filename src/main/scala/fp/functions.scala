package fp

object functions {

  def compose[A, B, C](fn1: A => B, fn2: B => C): A => C =
    ???

  def curry[A, B, C](fn: (A, B) => C): A => B => C =
    ???

  def uncurry[A, B, C](fn: A => B => C): (A, B) => C =
    ???

  object exercise1 {
    val itos: Function1[Int, String] = ???

    val stod: String => Double = ???

    val itod: Int => Double = itos.andThen(stod)
  }

  object exercise2 {
    type Error = String
    type Parser[A] = String => Either[Error, (String, A)]

    def or[A](left: Parser[A], right: Parser[A]): Parser[A] =
      str =>
        left(str) match {
          case Left(_) => right(str)
          case r => r
      }
  }

  object identity {
    def apply[T](t: T): T = ??? // TODO
  }
  // think of polimorphic function as functions that
  // take a type are return a function
  // A : Type => a : A => A

  val idint = identity.apply[Int] _
}
