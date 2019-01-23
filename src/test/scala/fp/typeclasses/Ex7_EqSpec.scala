package fp.typeclasses

import org.scalatest.{FlatSpec, Matchers}

class Ex7_EqSpec extends FlatSpec with Matchers {
  it should "work" in {
    assert(exerciseEq.check(1) === "It is one!")
    assert(exerciseEq.check(2) === "It is something else then 1")
  }
}
