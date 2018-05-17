package fp.typeclasses

object Id {
  type Id[A] = A

  // implement the IdMonad typeclass
  implicit val idMonad: Monad[Id] = ???

}
