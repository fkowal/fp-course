package fp.spring.Ex6

import com.fasterxml.jackson.module.scala.DefaultScalaModule
import fp.spring.spring4.infra.RestResponseEntityExceptionHandler
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.{Bean, Configuration, Import}
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.{ControllerAdvice, ExceptionHandler, ResponseStatus}

@SpringBootApplication
@Configuration
@ControllerAdvice
@Import(Array(classOf[RestResponseEntityExceptionHandler]))
class IdApp {

  @Bean
  def jackson: DefaultScalaModule = DefaultScalaModule

  @ExceptionHandler(Array(classOf[NoSuchElementException]))
  @ResponseStatus(HttpStatus.NOT_FOUND)
  def noSuchElementException(ex: NoSuchElementException): Unit = ()
}

object IdApp extends App {
  SpringApplication.run(classOf[IdApp], args: _*)
}
