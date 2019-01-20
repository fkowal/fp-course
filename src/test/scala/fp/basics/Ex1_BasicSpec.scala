package fp.basics

import org.scalatest.{FlatSpec, Matchers}

class Ex1_BasicSpec extends FlatSpec with Matchers {
  it should "pattern match" in {
    def goldilocks(expr: Any) = expr match {
      case ("porridge", _) ⇒ "eating"
      case ("chair", "Mama") ⇒ "sitting"
      case ("bed", "Baby") ⇒ "sleeping"
      case _ ⇒ "what?"
    }

    assert(goldilocks(("porridge", "Papa")) == ???)
    assert(goldilocks(("chair", "Mama")) == ???)
  }

  it should "decompose data" in {
    case class Address(street: String, city: String)
    case class User(name: String, age: Int, address: Address)

    val user = User("maciej", 22, Address("ulica", "miasto"))

    val (imie, miasto) = user match {
      case User(imie, _, Address(_, miasto)) => (imie, miasto)
    }

    assert(imie == ???)
    assert(miasto == ???)
  }

  it should "map to string" in {
    assert(List(1, 2, 3, 4).map(i => i.toString) == List())
  }

  it should "work for tuples" in {
    val t = (1, "2")
    val t2 = 1 -> "2"

    assert(t == t2)

    val t3 = (1, "a", 1L)
    val (i, s, l) = t3

    assert(i == 1)
    assert(s == "a")
    assert(l == 1L)
  }
}
