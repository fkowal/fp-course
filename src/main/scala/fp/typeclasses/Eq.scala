package fp.typeclasses

object exerciseEq extends App {

  def check(i: Int): String =
    if (i == "1") "It is one!" else "It is something else then 1"

  println(check(1))
  println(check(2))
}

/**
  * 1. Create Eq type class with method equalz checking for equality of two object of type A
  * 2. Create extension method ===
  * 3. Create instance for Int
  * 4. Reimplelemnt check method using Eq
  */
