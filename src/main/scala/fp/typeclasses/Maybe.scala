package fp.typeclasses

sealed trait Maybe[+A] {

  def map[B](f: A => B): Maybe[B] = this match {
    case Just(v) => Just(f(v))
    case Empty => Empty
  }

  def flatMap[B](f: A => Maybe[B]): Maybe[B] = this match {
    case Just(a) => f(a)
    case Empty => Empty
  }
}
case class Just[+A](value: A) extends Maybe[A]
case object Empty extends Maybe[Nothing]

object Maybe {
  @inline def apply[A: Maybe]: Maybe[A] = implicitly[Maybe[A]]

  def maybe[A](value: A): Maybe[A] = if (null == value) Empty else Just(value)

  def empty[A](): Maybe[A] = Empty

  def map2[A, B, C](m1: Maybe[A], m2: Maybe[B])(f: (A, B) => C): Maybe[C] =
    for {
      a <- m1
      b <- m2
    } yield f(a, b)

  def sequence[A](l: List[Maybe[A]]): Maybe[List[A]] = {
    if (l.isEmpty)
      Empty
    else {
      l.foldLeft(maybe(List[A]()))(map2(_, _)((lv, v) => lv :+ v))
    }
  }

  def lift[A, B](f: A => B): Maybe[A] => Maybe[B] = ma => ma.map(f)

  object Syntax {
    implicit class MaybeSyntax[A](a: A) {
      def maybe: Maybe[A] = Maybe.maybe(a)
    }
  }
}
