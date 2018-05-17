package fp.spring.springfp

import cats._
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.scala.DefaultScalaModule
import fp.spring.springfp.user.{User, UserController}
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
import reactor.ipc.netty.http.server.HttpServer

class SpringWebFluxApp {

  def start(port: Int) = {

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
      val server = HttpServer.create("localhost", port)

      server.newHandler(adapter)
    }

    // setup
//    import infra.Sync._ // uncomment this line for synchronous interpretation

    import infra.AsyncDomain._ // use asynchronous interpreter

    val server = runServer(SpringWebFluxApp.routing(controller))

    server.block()
  }
}

object SpringWebFluxApp extends App {
  new SpringWebFluxApp().start(8080)

  System.out.println("Press ENTER to exit.")
  System.in.read()

  def routing[F[_]](controller: UserController[F])(implicit nat: ~>[F, Mono]): RouterFunction[ServerResponse] = {
    route(
      GET("/user/{userId}/age"),
      request => {
        val userId = request.pathVariable("userId")
        ServerResponse.ok().body(nat(controller.functorRequired(userId)), classOf[Int])
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
}