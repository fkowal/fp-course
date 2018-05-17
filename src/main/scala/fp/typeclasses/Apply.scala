package fp.typeclasses

trait Apply[F[_]] extends Functor[F] {
  def ap[A, B](ff: F[A => B])(fa: F[A]): F[B]
}

object Apply {
  implicit val apOption = new Apply[Option] {
    override def ap[A, B](ff: Option[A => B])(fa: Option[A]): Option[B] =
      for {
        f <- ff
        a <- fa
      } yield f(a)

    override def map[A, B](fa: Option[A])(f: A => B): Option[B] =
      Functor.optionFunctor.map(fa)(f)
  }
}
