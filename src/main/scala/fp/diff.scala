package fp

import shapeless.{::, DepFn2, Generic, HList, HNil}

sealed trait Diff[A]

final case class Identical[A](value: A) extends Diff[A]

final case class Different[A](left: A, right: A) extends Diff[A]

object Diff {

  def apply[A](left: A, right: A): Diff[A] =
    if (left == right) Identical(left)
    else Different(left, right)
}

trait SimpleDelta[R <: HList] extends DepFn2[R, R] {
  type Out <: HList
}

object SimpleDelta {

  type Aux[I <: HList, O <: HList] = SimpleDelta[I] { type Out = O }

  implicit def hnilDelta: Aux[HNil, HNil] = new SimpleDelta[HNil] {

    type Out = HNil

    def apply(l: HNil, r: HNil): Out = HNil

  }

  implicit def hconsDelta[H, T <: HList, DT <: HList](implicit tailDelta: Aux[T, DT]): Aux[H :: T, Diff[H] :: DT] =
    new SimpleDelta[H :: T] {

      type Out = Diff[H] :: DT

      def apply(l: H :: T, r: H :: T): Out =
        Diff(l.head, r.head) :: tailDelta(l.tail, r.tail)
    }

  def apply[A, R <: HList](l: A, r: A)(implicit genA: Generic.Aux[A, R], delta: SimpleDelta[R]): delta.Out =
    delta(genA.to(l), genA.to(r))
}

trait Delta[R <: HList] extends DepFn2[R, R] {
  type Out <: HList
}

object Delta extends LowPriorityDelta {

  type Aux[R <: HList, O <: HList] = Delta[R] { type Out = O }

  implicit def hnilDelta: Aux[HNil, HNil] = new Delta[HNil] {
    override type Out = HNil

    override def apply(l: HNil, r: HNil): Out = HNil
  }

  implicit def hconsGenDelta[H, GH <: HList, DH <: HList, T <: HList, DT <: HList](
    implicit genH: Generic.Aux[H, GH],
    nested: Delta.Aux[GH, DH],
    tailDelta: Delta.Aux[T, DT]): Aux[H :: T, DH :: DT] = new Delta[H :: T] {
    override type Out = DH :: DT

    override def apply(l: H :: T, r: H :: T): Out =
      nested(genH.to(l.head), genH.to(r.head)) :: tailDelta(l.tail, r.tail)
  }

  def apply[A, R <: HList](l: A, r: A)(implicit genA: Generic.Aux[A, R], delta: Delta[R]): delta.Out =
    delta(genA.to(l), genA.to(r))
}

trait LowPriorityDelta {
  implicit def hconsDelta[H, T <: HList, DT <: HList](
    implicit tailDelta: Delta.Aux[T, DT]): Delta.Aux[H :: T, Diff[H] :: DT] = new Delta[H :: T] {
    override type Out = Diff[H] :: DT

    override def apply(l: H :: T, r: H :: T): Out =
      Diff(l.head, r.head) :: tailDelta(l.tail, r.tail)

  }
}

object DiffDemo extends App {
  case class User(name: String, age: Int)

  println(Diff(User("maciej", 12), User("jan", 12)))

  println(Delta(User("maciej", 12), User("jan", 12)))

  case class NestedClass(user: User, other: String)
  val n = NestedClass(User("maciej", 12), "ten")
  val n2 = NestedClass(User("maciej", 13), "ten")

  println(Delta(n, n2))
}
