package fp.typeclasses

// Functors are descriptions of computations
// map(fa)(id) == fa
// map(fa)(f compose g) == map(map(fa)(g), f)
trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
}

object Functor {
  implicit val optionFunctor: cats.Functor[Option] = ???

  object Syntax {
    implicit class FunctorSyntax[F[_], A](val fa: F[A]) extends AnyVal {
      def map[B](f: A => B)(implicit FF: cats.Functor[F]): F[B] = FF.map(fa)(f)
    }

  }
}
