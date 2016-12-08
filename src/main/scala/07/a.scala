package example07

object a {

// #example1
import unfiltered.request._
import unfiltered.response._
import unfiltered.jetty.SocketPortBinding

val binding = SocketPortBinding(host = "localhost", port = 8080)

val Simple = unfiltered.filter.Planify {
  case Path("/") & Accepts.Json(_) =>
    JsonContent ~> ResponseString("""{ "response": "Ok" }""")
}
unfiltered.jetty.Server.portBinding(binding).plan(Simple).run()
// #example1


// #example2
import unfiltered.directives._, Directives._

val Smart = unfiltered.filter.Planify { Directive.Intent {
  case Path("/") =>
    for {
      _ <- Accepts.Json
    } yield JsonContent ~> ResponseString("""{ "response": "Ok" }""")
} }
unfiltered.jetty.Server.portBinding(binding).plan(Smart).run()
// #example2


// #example3
val Sweet = unfiltered.filter.Planify { Directive.Intent.Path {
  case "/" =>
    for {
      _ <- Accepts.Json
    } yield JsonContent ~> ResponseString("""{ "response": "Ok" }""")
} }
unfiltered.jetty.Server.portBinding(binding).plan(Sweet).run()
// #example3

}
