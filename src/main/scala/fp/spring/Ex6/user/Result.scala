package fp.spring.Ex6.user

case class Result[A](result: A) {

  def flatMap[B](f: A => Result[B]): Result[B] =
    f(result)

  def map[B](f: A => B): Result[B] =
    Result(f(result))
}

object Result {}
