package example07


object e {

// #example1
import unfiltered.response._
import unfiltered.directives._
import unfiltered.directives.data.Requiring

case class OneBadParam(msg: String) extends Responder[Any] {
  def respond(res: HttpResponse[Any]): Unit =
    (BadRequest ~> ResponseString(msg + "\n"))(res)
}
// #example1


locally {
// #example2
implicit def required[T]: Requiring[T, ResponseFunction[Any]] = data.Requiring[T].fail(name =>
  OneBadParam(name + " is missing")
)
// #example2
}


// #example3
case class BadParam(msg: String) extends ResponseJoiner(msg)(
  msgs =>
    BadRequest ~> ResponseString(msgs.mkString("","\n","\n"))
)
// #example3



// #example4
implicit def required[T]: Requiring[T, BadParam] = data.Requiring[T].fail(name =>
  BadParam(name + " is missing")
)
// #example4

}
