package example08

import unfiltered.response._

object a {

// #example1
object Hello {
  val intent = unfiltered.Cycle.Intent[Any, Any] {
    case _ => ResponseString("Hello")
  }
}
// #example1


// #example2
val helloFilter =
  unfiltered.filter.Planify(Hello.intent)

val helloHandler =
  unfiltered.netty.cycle.Planify(Hello.intent)
// #example2

}
