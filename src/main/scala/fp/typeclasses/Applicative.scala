package fp.typeclasses
import simulacrum.typeclass

@typeclass
trait Applicative[F[_]] extends Apply[F] {
  def pure[A](x: A): F[A]
}

object Applicative {

  implicit val optionApplicative: Applicative[Option] = new Applicative[Option] {
    override def pure[A](x: A): Option[A] =
      ???

    override def zip[A, B](fa: Option[A], fb: Option[B]): Option[(A, B)] =
      ???

    override def map[A, B](fa: Option[A])(f: A => B): Option[B] =
      ???
  }
}
