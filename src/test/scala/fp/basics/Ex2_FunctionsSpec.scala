package fp.basics

import org.scalatest.FlatSpec

class Ex2_FunctionsSpec extends FlatSpec {
  it should "functions syntax" in {
    val fn1 = new Function[Int, String] {
      override def apply(v1: Int): String = v1.toString
    }

    val fn2: Int => String = i => i.toString

    def fn3(i: Int): String = i.toString

    assert(fn1.apply(1) == "1")
    assert(fn1(1) == "1")

    assert(fn2.apply(2) == "2")
    assert(fn2(2) == "2")

    assert(fn3(3) == "3")
  }

  it should "compose" in {
    def toDouble(i: Int): String = i.toString
    def toString(s: String): Double = s.toDouble

    val itodViaS = fp.functions.compose(toDouble, toString)

    assert(itodViaS(10) == 10d)
  }

  it should "curry" in {
    def fn(a: Int, b: Int) = a + b

    val curried = fp.functions.curry(fn)

    assert(curried(1)(2) == 3)

    val uncurried = fp.functions.uncurry(curried)

    assert(uncurried(1, 2) == 3)
  }

  it should "object syntax" in {
    object MySingleton {
      def example() = "example"

      def apply(s: String) = s.length
    }

    assert(MySingleton.example() == "example")

    assert(MySingleton.apply("test") == 4)
    assert(MySingleton("test") == 4)
  }
}
