package fp.typeclasses

import org.scalatest.{FlatSpec, Matchers}

class MonoidSpec extends FlatSpec with Matchers {
  it should "combine strings" in {
    val monoid = Monoid.monoidString
    assert(monoid.empty == "")
    assert(monoid.combine("a", "b") == "ab")

    assert(monoid.combine(monoid.combine("a", "b"), "c") == monoid.combine(a, monoid.combine("b", "c")))
  }
}
