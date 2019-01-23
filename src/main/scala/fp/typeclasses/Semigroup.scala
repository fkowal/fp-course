package fp.typeclasses

import simulacrum.typeclass

@typeclass
trait Semigroup[A] {
  def combine(x: A, y: A): A
}
