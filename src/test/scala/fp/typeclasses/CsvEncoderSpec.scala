package fp.typeclasses

import org.scalatest.{FlatSpec, Matchers}
import shapeless.HNil

class CsvEncoderSpec extends FlatSpec with Matchers {
  import CsvEncoder._

  it should "compile" in {
    "CsvEncoder[String]" should compile
  }

  it should "encode string" in {
    assert(CsvEncoder[String].encode("string") === List("string"))
  }

  it should "encode int" in {
    assert(encodeCsv[Int](123) === List("123"))
  }

  it should "encode boolean" in {
    assert(encodeCsv(true) === List("on"))
    assert(encodeCsv(false) === List("off"))
  }

  it should "support suffix syntax" in {
    import CsvEncoder.ops._
    assert(false.csv === List("off"))
  }

  it should "encode hlists" in {
    val hlist = 1 :: "abc" :: HNil

    assert(encodeCsv(hlist) === List("1", "abc"))
  }

  it should "encode any case class" in {
    case class User(name: String, age: Int)

    assert(encodeCsv(User("maciej", 22)) === List("maciej", "22"))
  }
}
