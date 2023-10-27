package example07

object c {

import unfiltered.jetty.SocketPortBinding

val binding = SocketPortBinding(host = "localhost", port = 8080)

locally {
// #example1
import unfiltered.request._
import unfiltered.response._
import unfiltered.directives._, Directives._

val intValue = data.as.Int.fail { (k,v) =>
  BadRequest ~> ResponseString(
    s"'$v' is not a valid int for $k"
  )
}

unfiltered.jetty.Server.portBinding(binding).plan(
  unfiltered.filter.Planify { Directive.Intent {
    case Path("/") =>
      for {
        a <- intValue named "a"
        b <- intValue named "b"
      } yield ResponseString(
        (a ++ b).sum.toString + "n"
      )
  } }
).run()
// #example1
}


locally {

import unfiltered.request._
import unfiltered.response._
import unfiltered.directives._

// #example2
implicit val implyIntValue =
  data.as.String ~> data.as.Int.fail { (k,v) =>
    BadRequest ~> ResponseString(
      s"'$v' is not a valid int for $k"
    )
  }

unfiltered.jetty.Server.portBinding(binding).plan(
  unfiltered.filter.Planify { Directive.Intent {
    case Path("/") =>
      for {
        a <- data.as.Option[Int] named "a"
        b <- data.as.Option[Int] named "b"
      } yield ResponseString(
        (a ++ b).sum.toString + "\n"
      )
  } }
).run()
// #example2
}

}
