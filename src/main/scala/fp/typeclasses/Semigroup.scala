package fp.typeclasses

trait Semigroup[A] {
  def combine(x: A, y: A): A
}
