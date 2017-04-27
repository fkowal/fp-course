package fp

import java.util

object patterns {

  trait Semigroup[A] {
    def combine(x: A, y: A): A
  }

  trait Monoid[A] extends Semigroup[A] {
    def empty: A
  }

  // Functors are descriptions of computations
  // map(fa)(id) == fa
  // map(fa)(f compose g) == map(map(fa)(g), f)
  trait Functor[F[_]] {
    def map[A, B](fa: F[A])(f: A => B): F[B]
  }

  object Functor {
    implicit val optionFunctor: Functor[Option] = ???

    object Syntax {
      implicit class FunctorSyntax[F[_], A](val fa: F[A]) extends AnyVal {
        def map[B](f: A => B)(implicit FF: Functor[F]): F[B] = FF.map(fa)(f)
      }

    }
  }
  val jul: java.util.List[Int] = null

  // TODO make this work
  // val mapped = jul.map(_ + 1)

  case class Box[T](value: T)

  // TODO make this compile without implementing map method for Box type
  // Box(123).map(_ + 1)

}
