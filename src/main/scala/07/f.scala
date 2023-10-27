package example07

object f {

// #example1
import unfiltered.request._
import unfiltered.response._
import unfiltered.directives._
import unfiltered.directives.data.Interpreter

def badParam(msg: String) =
  BadRequest ~> ResponseString(msg)

val evenInt = data.Conditional[Int](_ % 2 == 0).fail(
  (k, v) => badParam("not even: " + v)
)
// #example1


// #example2
implicit val intValue: Interpreter[Seq[String], Option[Int], ResponseFunction[Any]] =
  data.as.String ~> data.as.Int.fail(
    (k, v) => badParam("not an int: " + v)
  )
// #example2


// #example3
import unfiltered.jetty.SocketPortBinding

val binding = SocketPortBinding(host = "localhost", port = 8080)

unfiltered.jetty.Server.portBinding(binding).plan(
  unfiltered.filter.Planify { Directive.Intent {
    case Path("/") =>
      for {
        even <- evenInt named "even"
      } yield ResponseString(
        even.toString + "\n"
      )
  } }
).run()
// #example3


// #example4
case class Tool(name: String)
val toolStore = Map(
  1 -> Tool("Rock"),
  2 -> Tool("Paper"),
  3 -> Tool("Scissors")
)

val asTool = data.Fallible[Int,Tool](toolStore.get)

implicit def implyTool: Interpreter[Seq[String], Option[Tool], ResponseFunction[Any]] =
  data.as.String ~> data.as.Int ~> asTool.fail(
    (k, v) => badParam(s"'$v' is not a valid tool identifier")
  )
// #example4


// #example5
unfiltered.jetty.Server.portBinding(binding).plan(
  unfiltered.filter.Planify { Directive.Intent {
    case Path("/") =>
      for {
        tool <- data.as.Option[Tool] named "id"
      } yield ResponseString(
        tool.toString + "\n"
      )
  } }
).run()
// #example5

}
