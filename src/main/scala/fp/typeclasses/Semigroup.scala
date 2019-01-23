package fp.typeclasses

import simulacrum.{op, typeclass}

@typeclass
trait Semigroup[A] {

  @op("|+|")
  def combine(x: A, y: A): A
}
