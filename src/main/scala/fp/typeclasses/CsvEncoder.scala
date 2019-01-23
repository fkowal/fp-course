package fp.typeclasses

import shapeless.{::, Generic, HList, HNil}
import simulacrum.{op, typeclass}

@typeclass
trait CsvEncoder[A] {
  @op("csv") def encode(value: A): List[String]
}

object CsvEncoder {
  def pure[A](f: A => List[String]): CsvEncoder[A] = ???

  implicit val intEncode: CsvEncoder[Int] = ???

  implicit val stringEncode: CsvEncoder[String] = ???

  implicit val boolEncode: CsvEncoder[Boolean] = ???

  implicit val hnilEncode: CsvEncoder[HNil] = pure(_ => List())

  implicit def hconsEncode[H, T <: HList](
    implicit hencoder: CsvEncoder[H],
    tencoder: CsvEncoder[T]
  ): CsvEncoder[H :: T] =
    pure {
      case head :: tail =>
        hencoder.encode(head) ++ tencoder.encode(tail)
    }

  implicit def genericEnc[A, R](
    //implicit generic: Generic[A] { type Repr = R }, // Dependent Type
    implicit generic: Generic.Aux[A, R], // Dependent Type shorthand
    encoder: CsvEncoder[R]
  ): CsvEncoder[A] =
    pure(a => encoder.encode(generic.to(a)))

  def encodeCsv[A](a: A): List[String] = ???
}

object CsvMain extends App {
  import CsvEncoder._

//  println(encodeCsv("Dave"))
//  println(encodeCsv(123))
//  println(encodeCsv(true))

//  println(encodeCsv(List("a", "b")))

  case class IceCream(str: String, i: Int, bool: Boolean)
  case class Employee(str: String, i: Int, bool: Boolean)

//  println(encodeCsv(IceCream("Dave", 123, false)))
//
//  println(encodeCsv(Employee("name", 332, true)))
//  println(encodeCsv("str" :: 123 :: false :: HNil))

}
