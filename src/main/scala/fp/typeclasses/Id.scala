package fp.typeclasses

object Id {
  type Id[A] = A

  // implement the IdMonad typeclass
  implicit val idMonad: Monad[Id] = new Monad[Id] {
    override def flatMap[A, B](fa: Id[A])(f: A => Id[B]): Id[B] = f(fa)

    override def pure[A](x: A): Id[A] = x

    override def zip[A, B](fa: Id[A], fb: Id[B]): (A, B) = (fa, fb)

    override def map[A, B](fa: Id[A])(f: A => B): Id[B] = f(fa)
  }

}
