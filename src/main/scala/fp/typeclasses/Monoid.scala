package fp.typeclasses

trait Monoid[A] extends Semigroup[A] {
  def empty: A
}
