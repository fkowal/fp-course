package fp.spring.Ex6.user

case class Result[A](result: A)

object Result {

  def pure[A](a: A): Result[A] = ???

  def zip[A, B](a: Result[A], b: Result[B]): Result[(A, B)] = ???

  def map[A, B](fa: Result[A])(f: A => B): Result[B] = ???

  def flatMap[A, B](fa: Result[A])(f: A => Result[B]): Result[B] = ???
}
