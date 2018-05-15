package fp

import fp.typeclasses.Show

object patterns {

  trait Semigroup[A] {
    def combine(x: A, y: A): A
  }

  trait Monoid[A] extends Semigroup[A] {
    def empty: A
  }

  // Functors are descriptions of computations
  // map(fa)(id) == fa
  // map(fa)(f compose g) == map(map(fa)(g), f)
  trait Functor[F[_]] {
    def map[A, B](fa: F[A])(f: A => B): F[B]
  }

  object Functor {
    implicit val optionFunctor: Functor[Option] = ???

    object Syntax {
      implicit class FunctorSyntax[F[_], A](val fa: F[A]) extends AnyVal {
        def map[B](f: A => B)(implicit FF: Functor[F]): F[B] = FF.map(fa)(f)
      }

    }
  }
  val jul: java.util.List[Int] = null

  // TODO make this work
  // val mapped = jul.map(_ + 1)

  case class Box[T](value: T)

  // TODO make this compile without implementing map method for Box type
  // Box(123).map(_ + 1)

  trait Apply[F[_]] extends Functor[F] {
    def ap[A, B](ff: F[A => B])(fa: F[A]): F[B]
  }

  object Apply {
    implicit val apOption = new Apply[Option] {
      override def ap[A, B](ff: Option[A => B])(fa: Option[A]): Option[B] =
        for {
          f <- ff
          a <- fa
        } yield f(a)

      override def map[A, B](fa: Option[A])(f: A => B): Option[B] =
        Functor.optionFunctor.map(fa)(f)
    }
  }

  trait Applicative[F[_]] extends Apply[F] {
    def pure[A](x: A): F[A]
  }

  object Applicative {
    implicit val optionAppicative = new Applicative[Option] {
      override def pure[A](x: A): Option[A] = Option(x)

      override def ap[A, B](ff: Option[A => B])(fa: Option[A]): Option[B] = Apply.apOption.ap(ff)(fa)

      override def map[A, B](fa: Option[A])(f: A => B): Option[B] = ???
    }
  }

  trait Monad[F[_]] extends Applicative[F] {
    def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]
  }

  object Monad {
    implicit val opMonad = new Monad[Option] {
      override def pure[A](x: A): Option[A] = ???

      override def ap[A, B](ff: Option[A => B])(fa: Option[A]): Option[B] = ???

      override def map[A, B](fa: Option[A])(f: A => B): Option[B] = ???

      override def flatMap[A, B](fa: Option[A])(f: A => Option[B]): Option[B] = ???
    }
  }

  def toString[F[_], A](fa: F[A])(implicit f: Functor[F], s: Show[A]): F[String] =
    f.map(fa)(s.show)

//  val r = toString(Id"asdf")
//    fa.map(_.toInt)
//
//  val result = add(Future.successful("abc"))
//
//  result.value

}
