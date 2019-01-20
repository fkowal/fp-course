package fp.typeclasses

import org.scalatest.{FlatSpec, Matchers}

class Ex4_MaybeSpec extends FlatSpec with Matchers {
  import Maybe._

  it should "map" in {
    assert(Just(1).map(_ + 1) == Just(2))

    assert(Maybe.empty[Int]().map(_ + 1) == Empty)
  }

  it should "map2" in {
    val maybeAB = map2(Just(1), Just(2))(_ + _)

    assert(maybeAB == Just(3))
  }

  it should "sequence" in {
    val maybeList = sequence(List(Just(1), Just(2)))

    assert(maybeList == Just(List(1, 2)))
  }

  it should "lift" in {
    def fn(i: Int) = i + 1
    val lifted = lift(fn)

    assert(lifted(Just(1)) === Just(2))
  }

  it should "support maybe syntax" in {
    import Maybe.Syntax._
    "3.maybe" should compile
  }
}
