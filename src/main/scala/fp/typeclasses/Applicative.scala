package fp.typeclasses
import simulacrum.typeclass

@typeclass
trait Applicative[F[_]] extends Apply[F] {
  def pure[A](x: A): F[A]
}

object Applicative {

  implicit val optionApplicative: Applicative[Option] = new Applicative[Option] {
    override def pure[A](x: A): Option[A] =
      Option(x)

    override def zip[A, B](fa: Option[A], fb: Option[B]): Option[(A, B)] =
      Apply.apOption.zip(fa, fb)

    override def map[A, B](fa: Option[A])(f: A => B): Option[B] =
      fa.map(f)
  }
}
