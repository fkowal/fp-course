package fp.spring.springfp

import cats._
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import fp.spring.springfp.user.{User, UserController, UserDetails}
import org.springframework.http.codec.json.{Jackson2JsonDecoder, Jackson2JsonEncoder}
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter
import org.springframework.web.reactive.function.BodyExtractors
import org.springframework.web.reactive.function.server.RequestPredicates.{GET, POST}
import org.springframework.web.reactive.function.server.RouterFunctions.route
import org.springframework.web.reactive.function.server.{
  HandlerStrategies,
  RouterFunction,
  RouterFunctions,
  ServerResponse
}
import reactor.core.publisher.Mono
import reactor.netty.http.server.HttpServer

class SpringWebFluxApp {

  def start(routing: RouterFunction[ServerResponse], port: Int) = {

    def runServer(router: RouterFunction[ServerResponse]) = {
      val objectMapper = new ObjectMapper()
      objectMapper.registerModule(DefaultScalaModule)

      val httpHandler = RouterFunctions.toHttpHandler(
        router,
        HandlerStrategies
          .builder()
          .codecs(configurer => {
            configurer.defaultCodecs.jackson2JsonEncoder(new Jackson2JsonEncoder(objectMapper))
            configurer.defaultCodecs.jackson2JsonDecoder(new Jackson2JsonDecoder(objectMapper))
          })
          .build()
      )
      val adapter = new ReactorHttpHandlerAdapter(httpHandler)
      val server = HttpServer
        .create()
        .handle(adapter)
        .host("localhost")
        .port(port)

      server
    }

    val server = runServer(routing)

    server.bindNow()
  }
}

object SpringWebFluxApp extends App {
  val routing = routingBuilder(infra.AsyncDomain.controller)(infra.AsyncDomain.idMonoNat)

//  val routing = routingBuilder(infra.Sync.controller)(infra.Sync.idMonoNat)

  new SpringWebFluxApp().start(routing, port = 8080)

  System.out.println("Press ENTER to exit.")
  System.in.read()

  def routingBuilder[F[_]](controller: UserController[F])(implicit nat: ~>[F, Mono]): RouterFunction[ServerResponse] = {
    route(
      GET("/user/{userId}/age"),
      request => {
        val userId = request.pathVariable("userId")
        ServerResponse.ok().body(nat(controller.getAge(userId)), classOf[Int])
      }
    ).andRoute(GET("/user/{userId}"), request => {
        val userId = request.pathVariable("userId")
        ServerResponse.ok().body(nat(controller.get(userId)), classOf[User])
      })
      .andRoute(
        POST("/user"),
        request => {

          val result: Mono[Option[User]] =
            request
              .body(BodyExtractors.toMono(classOf[User]))
              .flatMap(usr => nat(controller.post(usr)))

          ServerResponse
            .ok()
            .body(result, classOf[Option[User]])
        }
      )
      .andRoute(
        GET("/user/{userId}/details"),
        request => {
          val userId = request.pathVariable("userId")

          ServerResponse
            .ok()
            .body(nat(controller.userDetails(userId)), classOf[UserDetails])
        }
      )

  }
}
