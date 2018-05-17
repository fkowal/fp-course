package fp.typeclasses

trait Monoid[A] extends Semigroup[A] {
  def empty: A
}

object Monoid {
  implicit val monoidString: Monoid[String] = new Monoid[String] {
    override def empty: String = ""

    override def combine(x: String, y: String): String = x + y
  }
}
