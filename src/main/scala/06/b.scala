package example06

object b {

// #example1
import unfiltered.request._
import unfiltered.response._
val pEcho = unfiltered.filter.Planify {
  case Params(params) =>
    ResponseString(params("test").toString)
}
// #example1

// #example2
unfiltered.jetty.Server.anylocal.plan(pEcho).run()
// #example2

// #example3
object Test extends Params.Extract("test", Params.first)
// #example3


// #example4
object NonEmptyTest extends Params.Extract(
  "test",
  Params.first ~> Params.nonempty
)
// #example4


// #example5
object NotShortTest extends Params.Extract(
  "test",
  Params.first ~> { p: Option[String] =>
    p.filter { _.length > 4 }
  }
)
// #example5


// #example6
object NotShortTest2 extends Params.Extract(
  "test",
  Params.first ~> Params.pred { _.length > 4 }
)
// #example6


// #example7
object Pos extends Params.Extract(
  "pos",
  Params.first ~> Params.int ~>
    Params.pred { _ > 0 }
)
object Neg extends Params.Extract(
  "neg",
  Params.first ~> Params.int ~>
    Params.pred { _ < 0 }
)
val intEcho = unfiltered.filter.Planify {
  case Params(Pos(pos) & Neg(neg)) =>
    ResponseString("%d %d".format(pos,neg))
}
unfiltered.jetty.Server.anylocal.plan(intEcho).run()
// #example7

}
