package fp

object functions {

  object identity {
    def apply[T](): T = ??? // TODO
  }
  // think of polimorphic function as functions that
  // take a type are return a function
  // A : Type => a : A => A

  val idint = identity.apply[Int] _
}
