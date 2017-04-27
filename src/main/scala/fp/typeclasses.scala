package fp

object typeclasses {
  trait Show[A] {
    def show(value: A): String
  }

  object Show {
//    def apply[A](implicit s: Show[A]): Show[A] = s
    def apply[A: Show](): Show[A] = Show[A]

    def pure[A](func: A => String): Show[A] = ???

    implicit val showInt: Show[Int] = ???

    implicit val showString: Show[String] = ???

    implicit val showBoolean: Show[String] = ???
  }

  trait CsvEncoder[A] {
    def encode(value: A): List[String]
  }

  object CsvEncoder {
    def pure[A](f: A => List[String]): CsvEncoder[A] = ???

    implicit val intEncode: CsvEncoder[Int] = ???

    implicit val stringEncode: CsvEncoder[String] = ???
  }

  case class User(name: String, age: Int)


}
