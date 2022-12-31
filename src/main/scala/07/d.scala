package example07

import unfiltered.jetty.SocketPortBinding

object d {

val binding = SocketPortBinding(host = "localhost", port = 8080)

// #example1
import unfiltered.request._
import unfiltered.response._
import unfiltered.directives._, Directives._

implicit def required[T] = data.Requiring[T].fail(name =>
  BadRequest ~> ResponseString(name + " is missing\n")
)
// #example1


// #example2
unfiltered.jetty.Server.portBinding(binding).plan(
  unfiltered.filter.Planify { Directive.Intent {
    case Path("/") =>
      for {
        opt <- data.as.Option[String] named "opt"
        req <- data.as.Required[String] named "req"
      } yield ResponseString(
        s"opt: $opt req: $req"
      )
  } }
).run()
// #example2


// #example3
unfiltered.jetty.Server.portBinding(binding).plan(
  unfiltered.filter.Planify { Directive.Intent {
    case Path("/") =>
      for {
        in <- data.as.BigInt ~> required named "in"
      } yield ResponseString(
        (in % 10).toString + "\n"
      )
  } }
).run()
// #example3


// #example4
unfiltered.jetty.Server.portBinding(binding).plan(
  unfiltered.filter.Planify { Directive.Intent {
    case Path("/") =>
      for {
        in <- data.as.BigInt.fail((k,v) =>
          BadRequest ~> ResponseString(s"'$v' is not a valid int for $k\n")
        ) ~> required named "in"
      } yield ResponseString(
        (in % 10).toString + "\n"
      )
  } }
).run()
// #example4

}
