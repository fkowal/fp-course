package fp.typeclasses

import org.scalatest.{FlatSpec, Matchers}

class Ex5_ShowSpec extends FlatSpec with Matchers {
  behavior of "Show"

  def show[T](t: T)(implicit s: Show[T]) = s.show(t)

  it should "display string" in {
//    assert(Show[String].show("abc") === "abc")
  }

  it should "display int" in {
//    assert(show(123) === "123")
  }

  it should "display a List" in {
//    assert(show(List(1, 2, 3)) === "[1,2,3]")
  }
}
