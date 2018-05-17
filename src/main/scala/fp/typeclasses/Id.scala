package fp.typeclasses

object Id {
  type Id[A] = A

  // implement the IdMonad typeclass
  implicit val idMonad: Monad[Id] = new Monad[Id] {
    override def flatMap[A, B](fa: Id[A])(f: A => Id[B]): Id[B] = f(fa)

    override def pure[A](x: A): Id[A] = x

    override def ap[A, B](ff: Id[A => B])(fa: Id[A]): Id[B] = ff(fa)

    override def map[A, B](fa: Id[A])(f: A => B): Id[B] = f(fa)
  }

}
