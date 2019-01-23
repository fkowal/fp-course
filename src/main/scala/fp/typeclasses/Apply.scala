package fp.typeclasses
import simulacrum.typeclass

@typeclass
trait Apply[F[_]] extends Functor[F] {
  def zip[A, B](fa: F[A], fb: F[B]): F[(A, B)]
}

object Apply {
  type *[A, B] = (A, B)
// F[A] * F[B] = F[A * B]
  implicit val apOption: Apply[Option] = new Apply[Option] {

    override def zip[A, B](fa: Option[A], fb: Option[B]): Option[(A, B)] =
      (fa, fb) match {
        case (Some(a), Some(b)) => Option((a, b))
        case _ => None
      }

    override def map[A, B](fa: Option[A])(f: A => B): Option[B] =
      Functor.optionFunctor.map(fa)(f)
  }
}
