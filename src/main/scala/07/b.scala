package example07

object b {

// #example1
import unfiltered.request._
import unfiltered.response._
import unfiltered.directives._, Directives._
import unfiltered.jetty.SocketPortBinding

val binding = SocketPortBinding(host = "localhost", port = 8080)

unfiltered.jetty.Server.portBinding(binding).plan(
  unfiltered.filter.Planify { Directive.Intent {
    case Path("/") =>
      for {
        in <- parameterValues("in")
      } yield ResponseString(in.toString)
  } }
).run()
// #example1


// #example2
unfiltered.jetty.Server.portBinding(binding).plan(
  unfiltered.filter.Planify { Directive.Intent {
    case Path("/") =>
      for {
        in <- data.as.Int named "in"
      } yield ResponseString(in.toString)
  } }
).run()
// #example2


// #example3
unfiltered.jetty.Server.portBinding(binding).plan(
  unfiltered.filter.Planify { Directive.Intent {
    case Path("/") =>
      for {
        in <- data.as.Int.fail { (k,v) =>
          BadRequest ~> ResponseString(
            s"'$v' is not a valid int for $k"
          )
        } named "in"
      } yield ResponseString(in.toString)
  } }
).run()
// #example3

}
