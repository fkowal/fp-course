package fp.basics

import fp.basics.Lists.Car
import org.scalatest.{FlatSpec, Matchers}

object Lists {
  val CARS = List("Ford Fiesta", "Ford Mondeo", "Ford Mustang", "BMW X5", "BMW X6", "Fiat 126p")

  def getBrands(): List[Brand] = CARS.map(Car.apply).map(_.brand)

  def getBrandSet(): Set[Brand] = getBrands().toSet

  // Sorted by brand name.
  def getBrandArray(): Array[Brand] = getBrands().toArray

  def getModels(): Set[Model] = CARS.map(Car.apply).map(_.model).toSet

  // Sorted by brand name, then model name.
  def getCars(): List[Car] = CARS.map(Car.apply)

  /** See [toMap] */
  def getModelMap(): Map[Model, Car] = getCars().map(c => c.model -> c).toMap

  /** See [groupBy] */
  def getCarBrandMap(): Map[Brand, List[Car]] = getCars().groupBy(_.brand)

  /** See [flatMap] */
  def getAllModels(carBrandMap: Map[Brand, List[Car]]): Set[Model] =
    carBrandMap.values.flatMap(l => l.map(_.model)).toSet

  /**
    * All Ford model names which start with M.
    */
  def getFordsMModels(): Set[String] = getModels().filter(_.startsWith("M"))

  type Brand = String
  type Model = String
  case class Car(
    brand: Brand,
    model: Model
  )

  object Car {

    def apply(s: String): Car = {
      val parts = s.split(" ", 2)
      Car(parts(0), parts(1))
    }
  }

}

class ListTest extends FlatSpec with Matchers {
  it should "create proper brands" in {
    Lists.getBrands() should contain allElementsOf List("BMW", "Fiat", "Ford")
  }

  it should "should create proper brandSet" in {
    assert(Lists.getBrandSet == Set("BMW", "Fiat", "Ford"))
  }

  it should "create proper brandArray" in {
    Lists.getBrandArray() should contain allElementsOf Array("BMW", "Fiat", "Ford")
  }

  it should "create proper models" in {
    assert(Lists.getModels === Set("Fiesta", "Mondeo", "Mustang", "X5", "X6", "126p"))
  }

  it should "create proper cars" in {
    Lists.getCars() should contain allElementsOf List(
      Car("BMW", "X5"),
      Car("BMW", "X6"),
      Car("Fiat", "126p"),
      Car("Ford", "Fiesta"),
      Car("Ford", "Mondeo"),
      Car("Ford", "Mustang"))
  }

  it should "create proper model map" in {
    assert(
      Lists.getModelMap === Map(
        "X5" -> Car("BMW", "X5"),
        "X6" -> Car("BMW", "X6"),
        "126p" -> Car("Fiat", "126p"),
        "Fiesta" -> Car("Ford", "Fiesta"),
        "Mondeo" -> Car("Ford", "Mondeo"),
        "Mustang" -> Car("Ford", "Mustang")
      ))
  }

  it should "create proper carModelMap" in {
    assert(
      Lists.getCarBrandMap ===
        Map(
          "BMW" ->
            List(Car("BMW", "X5"), Car("BMW", "X6")),
          "Fiat" ->
            List(Car("Fiat", "126p")),
          "Ford" ->
            List(Car("Ford", "Fiesta"), Car("Ford", "Mondeo"), Car("Ford", "Mustang"))
        ))
  }

  it should "create proper fordsMModels" in {
    assert(Lists.getFordsMModels === Set("Mondeo", "Mustang"))
  }

  it should "create proper models set" in {
    assert(
      Lists.getAllModels(Map(
        "BMW" -> List(Car("BMW", "X5"), Car("BMW", "X6")),
        "Fiat" -> List(Car("Fiat", "126p")),
        "Ford" -> List(Car("Ford", "Fiesta"), Car("Ford", "Mondeo"), Car("Ford", "Mustang"))
      )) === Set("Fiesta", "Mondeo", "Mustang", "X5", "X6", "126p"))
  }
}
