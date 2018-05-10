package fp.typeclasses

sealed trait Maybe[+A] {

  def map[B](f: A => B): Maybe[B] = ???

  def flatMap[B](f: A => Maybe[B]): Maybe[B] = ???
}
case class Just[+A](value: A) extends Maybe[A]
case object Empty extends Maybe[Nothing]

object Maybe {
  @inline def apply[A: Maybe]: Maybe[A] = implicitly[Maybe[A]]

  def just[A](value: A): Maybe[A] = ???

  def empty[A](): Maybe[A] = ???

  def map2[A, B, C](m1: Maybe[A], m2: Maybe[B])(f: (A, B) => C): Maybe[C] =
    ???

  def sequence[A](l: List[Maybe[A]]): Maybe[List[A]] = ???

  def lift[A, B](f: A => B): Maybe[A] => Maybe[B] = ???

  object Syntax {
    implicit class MaybeSyntax[A](a: A) {
      def maybe: Maybe[A] = Maybe.just(a)
    }
  }
}
