package fp.typeclasses

object exerciseEq extends App {

  import Eq.Syntax._

  def check(i: Int): String =
    if (i === 1) "It is one!" else "It is something else then 1"
  //if (i == "1") "It is one!" else "It is something else then 1" // this should not compile

  println(check(1))
  println(check(2))
}

/**
  * 1. Create Eq type class with method equalz checking for equality of two object of type A
  * 2. Create extension method ===
  * 3. Create instance for Int
  * 4. Reimplelemnt check method using Eq
  */
trait Eq[T] {
  def eqv(a: T, b: T): Boolean
}

object Eq {
  @inline def apply[F](implicit F: Eq[F]): Eq[F] = F

  def pure[T](fn: (T, T) => Boolean): Eq[T] = (a: T, b: T) => fn(a, b)

  def equalA[A]: Eq[A] = pure(_ == _)

  implicit val intEq: Eq[Int] = equalA[Int]

  object Syntax {
    implicit class EqSyntax[T: Eq](t: T) {
      def ===(t2: T): Boolean = Eq[T].eqv(t, t2)
    }
  }
}
