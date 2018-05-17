package fp.spring.springfp

import cats._
import cats.arrow.FunctionK
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import org.springframework.http.codec.json.{Jackson2JsonDecoder, Jackson2JsonEncoder}
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.server.RequestPredicates.{GET, POST}
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.{HandlerStrategies, RouterFunctions, ServerResponse}
import reactor.core.publisher.Mono
import reactor.ipc.netty.http.server.HttpServer

class SpringWebFluxApp {

  def routing[F[_]](controller: UserController[F], nat: FunctionK[F, Mono]) = {
    route(
      GET("/user/{userId}/age"),
      request => {
        val userId = request.pathVariable("userId")
        ServerResponse.ok().body(nat(controller.age(userId)), classOf[Int])
      }
    ).andRoute(
      POST("/user"),
      request => {

        request
          .body(BodyExtractors.toMono(classOf[User]))
          .flatMap(usr => nat(controller.put(usr)))
          .subscribe(x => println(x))
        ServerResponse
          .ok()
          .syncBody(
            ()
          )
      }
    )

  }

  implicit val monoNat = FunctionK.id[Mono]
  implicit val idMonoNat = new ~>[Id, Mono] {
    override def apply[A](fa: Id[A]): Mono[A] = Mono.just(fa)
  }
  implicit val monoFunctor = new Functor[Mono] {
    override def map[A, B](fa: Mono[A])(f: A => B): Mono[B] = fa.map(a => f(a))
  }

  def start(port: Int) = {
    val userRepository = new MonoRepo
    userRepository.save(User("123", "maciej", 32))

    val controller = new UserController[Mono](userRepository)
    val objectMapper = new ObjectMapper()
    objectMapper.registerModule(DefaultScalaModule)

    val httpHandler = RouterFunctions.toHttpHandler(
      routing(controller, monoNat),
      HandlerStrategies
        .builder()
        .codecs(configurer => {
          configurer.defaultCodecs.jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper))
          configurer.defaultCodecs.jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper))
        })
        .build()
    )
    val adapter = new ReactorHttpHandlerAdapter(httpHandler)
    val server = HttpServer.create("localhost", port)

    server.newHandler(adapter).block()
  }
}

object SpringWebFluxApp extends App {
  new SpringWebFluxApp().start(8080)

  System.out.println("Press ENTER to exit.")
  System.in.read()
}
