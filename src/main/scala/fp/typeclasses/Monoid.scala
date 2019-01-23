package fp.typeclasses
import simulacrum.typeclass

@typeclass
trait Monoid[A] extends Semigroup[A] {
  def empty: A
}

object Monoid {
  implicit val monoidString: Monoid[String] = ???
}
