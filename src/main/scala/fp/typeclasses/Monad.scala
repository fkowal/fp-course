package fp.typeclasses
import simulacrum.{op, typeclass}

@typeclass
trait Monad[F[_]] extends Applicative[F] {
  @op(">>=") def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]
}

object Monad {
  implicit val opMonad: Monad[Option] = ???

}
