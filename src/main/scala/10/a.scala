package example10

object a {

// #example1
import unfiltered.response._
val hello = unfiltered.netty.cycle.Planify {
  case _ => ResponseString("hello world")
}
unfiltered.netty.Server.http(8080).plan(hello).run()
// #example1

}
