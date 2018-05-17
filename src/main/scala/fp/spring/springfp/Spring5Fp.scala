package fp.spring.springfp

import cats.Id
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import fp.spring.springfp.infra.Sync
import fp.spring.springfp.user._
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.{Bean, Configuration}
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.{ControllerAdvice, ExceptionHandler, ResponseStatus}
import reactor.core.publisher.Mono

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
  def memoryRepo: UserRepository[Id] = Sync.repo

  @Bean
  def monadId = Sync.idMonad

  //  @Bean
//  def functorId = Functor[Id]

//  @Bean
//  def applicativeId = Applicative[Id]

//
//  @Bean
//  def usdf = new UserDetailService[Id] {
//    override def getUserDetails(user: User): Id[UserDetails] = UserDetails(user.userId)
//  }

//  @Bean
//  def functorId = Functor[Id]
//
  @Bean
  def usdf = new UserDetailService[Mono] {
    override def getUserDetails(user: User): Mono[UserDetails] = Mono.just(UserDetails(user.userId))
  }
//
//  @Bean
//  def functorMono = AsyncDomain.monadMono
//
//  @Bean
//  def monoRepo: UserRepository[Mono] = AsyncDomain.repo
}

object Spring5Fp extends App {
  SpringApplication.run(classOf[Spring5Fp], args: _*)
}
