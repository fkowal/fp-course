package fp.typeclasses

trait Applicative[F[_]] extends Apply[F] {
  def pure[A](x: A): F[A]
}

object Applicative {
  def apply[F[_]](implicit applicative: Applicative[F]): Applicative[F] = applicative

  implicit val optionAppicative = new Applicative[Option] {
    override def pure[A](x: A): Option[A] = Option(x)

    override def ap[A, B](ff: Option[A => B])(fa: Option[A]): Option[B] = Apply.apOption.ap(ff)(fa)

    override def map[A, B](fa: Option[A])(f: A => B): Option[B] = ???
  }
}
