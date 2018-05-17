package fp

class types {
  // domain Food: Vegetables, Meat, Fruit
  // codomain Feeling: Like, Love, Hate
  sealed trait Food
  case class Vegetables() extends Food
  case object Meat extends Food
  case object Fruit extends Food

  trait Feeling
  case object Like extends Feeling
  case object Hate extends Feeling

  def like(food: Food): Feeling = food match {
    case Vegetables() => Like
    case Meat => Like
    case _ => Hate
  }

  def f() = if (true) "abc" else "def"

  //  Int = *
  //  * => *
  //  fn(a): b
  // List[A] = * => *, List[_]
  // 1. scala.collection.List
  // 2. F[_, _] = (*,*) => *, _[_,_]
  // 3. Option = * => *, _[_]
  // 4. Int = * = _
  // 5. T[_[_], _] = (* => *, *) => *, _[_[_], _]

  //  List[A], Option[A],

  //  val l: MyBox[String]
}
