package fp.typeclasses

import shapeless.{::, Generic, HList, HNil}

trait CsvEncoder[A] {
  def encode(value: A): List[String]
}

object CsvEncoder {
  def apply[A: CsvEncoder](): CsvEncoder[A] = implicitly[CsvEncoder[A]]

  def pure[A](f: A => List[String]): CsvEncoder[A] = new CsvEncoder[A] {
    override def encode(value: A): List[String] = f(value)
  }

  implicit val intEncode: CsvEncoder[Int] = pure(i => List(i.toString))

  implicit val stringEncode: CsvEncoder[String] = pure(s => List(s))

  implicit val boolEncode: CsvEncoder[Boolean] = pure(b => if (b) List("on") else List("off"))

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

  object CsvOps {
    implicit class CsvSyntax[A](a: A) {
      def csv(implicit csvEncoder: CsvEncoder[A]): List[String] = csvEncoder.encode(a)
    }
  }

  def encodeCsv[A](a: A)(implicit csvEncoder: CsvEncoder[A]): List[String] = csvEncoder.encode(a)
}

object CsvMain extends App {

  def encodeCsv[A](value: A)(implicit enc: CsvEncoder[A]): List[String] =
    enc.encode(value)

//  println(encodeCsv("Dave"))
//  println(encodeCsv(123))
//  println(encodeCsv(true))

//  println(encodeCsv(List("a", "b")))

  //  println(encodeCsv(HNil))
  //  println(encodeCsv[HNil](HNil))
  case class IceCream(str: String, i: Int, bool: Boolean)
  case class Employee(str: String, i: Int, bool: Boolean)

//  println(encodeCsv(IceCream("Dave", 123, false)))
//
//  println(encodeCsv(Employee("name", 332, true)))
//  println(encodeCsv("str" :: 123 :: false :: HNil))

  //  println(encodeCsv(Company(Employee("a", 1, false)))) // needs lazy to skip over diverging implicit expansion for typ
}
