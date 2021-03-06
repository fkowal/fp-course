package fp.spring.springfp

import cats.Id
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import fp.spring.spring4.infra.RestResponseEntityExceptionHandler
import fp.spring.springfp.infra.Sync
import fp.spring.springfp.infra.AsyncDomain
import fp.spring.springfp.user._
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.{Bean, Configuration, Import}
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.{ControllerAdvice, ExceptionHandler, ResponseStatus}
import reactor.core.publisher.Mono

@SpringBootApplication
@Configuration
@ControllerAdvice
@Import(Array(classOf[RestResponseEntityExceptionHandler]))
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

  @Bean
  def userDetailService = Sync.UserDetailServiceImpl

//  @Bean
//  def monadMono = AsyncDomain.monadMono
//
//  @Bean
//  def monoRepo: UserRepository[Mono] = AsyncDomain.repo
//
//  @Bean
//  def userDetailService = AsyncDomain.UserDetailServiceImpl
}

object Spring5Fp extends App {
  SpringApplication.run(classOf[Spring5Fp], args: _*)
}
