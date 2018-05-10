package fp


object mylist {
  sealed trait Lst[T]
  case object End extends Lst[Nothing]
  case class Box[T](head: T, tail: Lst[T])

}
