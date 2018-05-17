package fp.typeclasses

trait Monad[F[_]] extends Applicative[F] {
  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]
}

object Monad {
  implicit val opMonad = new Monad[Option] {
    override def pure[A](x: A): Option[A] = ???

    override def ap[A, B](ff: Option[A => B])(fa: Option[A]): Option[B] = ???

    override def map[A, B](fa: Option[A])(f: A => B): Option[B] = ???

    override def flatMap[A, B](fa: Option[A])(f: A => Option[B]): Option[B] = ???
  }
}
