package page01

object Example1 {

// #example1
import unfiltered.request._
import unfiltered.response._

val echo = unfiltered.filter.Planify {
  case Path(Seg(p :: Nil)) => ResponseString(p)
}
// #example1


// #example2
unfiltered.jetty.Server.anylocal.plan(echo).run()
// #example2


// #example3
val echoNice = unfiltered.filter.Planify {
  case Path(Seg(p :: Nil)) => ResponseString(p)
  case _ => ResponseString(
    "I can echo exactly one path element."
  )
}
unfiltered.jetty.Server.anylocal.plan(echoNice).run()
// #example3


// #example4
val nice = unfiltered.filter.Planify {
  case _ => ResponseString(
    "I can echo exactly one path element."
  )
}
unfiltered.jetty.Server.anylocal.plan(echo).plan(nice).run()
// #example4

}
