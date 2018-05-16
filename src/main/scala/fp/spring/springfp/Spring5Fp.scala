package fp.spring.springfp

import cats.{Functor, Id}
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.{ControllerAdvice, ExceptionHandler, ResponseStatus}

@SpringBootApplication
@Configuration
@ControllerAdvice
class Spring5Fp {

  @Bean
  def jackson: DefaultScalaModule = DefaultScalaModule

  @ExceptionHandler(Array(classOf[NoSuchElementException]))
  @ResponseStatus(HttpStatus.NOT_FOUND)
  def noSuchElementException(ex: NoSuchElementException): Unit = ()

  @Bean
  def memoryRepo: UserRepository[Id] = new InMemoryRepository

  @Bean
  def functorId = Functor[Id]
//
//  @Bean
//  def functorMono = new Functor[Mono] {
//    override def map[A, B](fa: Mono[A])(f: A => B): Mono[B] = fa.map(a => f(a))
//  }
//
//  @Bean
//  def monoRepo: UserRepository[Mono] = new MonoRepo
}

object Spring5Fp extends App {
  SpringApplication.run(classOf[Spring5Fp], args: _*)
}
