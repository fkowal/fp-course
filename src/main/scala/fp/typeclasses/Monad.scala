package fp.typeclasses
import simulacrum.{op, typeclass}

@typeclass
trait Monad[F[_]] extends Applicative[F] {

  @op(">>=", alias = true)
  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]
}

object Monad {
  implicit val opMonad: Monad[Option] = new Monad[Option] {
    override def flatMap[A, B](fa: Option[A])(f: A => Option[B]): Option[B] =
      fa.flatMap(f)

    override def pure[A](x: A): Option[A] =
      Option(x)

    override def zip[A, B](fa: Option[A], fb: Option[B]): Option[(A, B)] =
      (fa, fb) match {
        case (Some(a), Some(b)) => Some((a, b))
        case _ => None
      }

    override def map[A, B](fa: Option[A])(f: A => B): Option[B] =
      fa.map(f)
  }

}
