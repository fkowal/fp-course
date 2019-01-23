package fp.typeclasses
import simulacrum.typeclass

// Functors are descriptions of computations
// map(fa)(id) == fa
// map(fa)(f compose g) == map(map(fa)(g), f)
@typeclass
trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
}

object Functor {
  implicit val optionFunctor: cats.Functor[Option] = ???

}
