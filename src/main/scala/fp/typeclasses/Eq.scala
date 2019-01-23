package fp.typeclasses
import simulacrum.{op, typeclass}

object exerciseEq extends App {

  import Eq.ops._

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
@typeclass
trait Eq[T] {
  @op("===") def eqv(a: T, b: T): Boolean
}

object Eq {

  def pure[T](fn: (T, T) => Boolean): Eq[T] = (a: T, b: T) => fn(a, b)

  def equalA[A]: Eq[A] = pure(_ == _)

  implicit val intEq: Eq[Int] = equalA[Int]

  implicit val stringEq: Eq[String] = equalA[String]
}
