package fp.typeclasses

import fp.typeclasses.Id._
import Monad.Syntax._
//import cats.Id
//import cats.implicits._
import org.scalatest.Matchers
import org.scalatest.FlatSpec

class IdSpec extends FlatSpec with Matchers {
  it should "support functor mapping" in {
    val idInt: Id[Int] = 3

    "idInt.map(_ + 1) == 4" should compile
//    assert(idInt.map(_ + 1) == 4) // uncomment me after you've implemented the monad
  }

// uncomment after above test passes

//  it should "support flatMapping" in {
//    def fn(i: Int): Id[Int] = i + 1
//
//    val result: (Int, Int) = for {
//      i <- fn(0)
//      j <- fn(i)
//    } yield (i, j)
//
//    assert(result._1 == 1)
//    assert(result._2 == 2)
//  }
//
//  it should "support Applicative[Id].pure" in {
//    val idInt: Id[Int] = Applicative[Id].pure(123)
//
//    assert(idInt == 123)
//  }
}
