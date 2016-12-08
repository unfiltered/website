object Example3 {

locally {
// #example1
import unfiltered.request.{Path => UFPath, _}
import unfiltered.response._

object AsyncPlan extends unfiltered.filter.async.Plan  {
  def intent = {
    case GET(UFPath("/pass")) => Pass
    case req@GET(UFPath("/async")) =>
      //"respond" is usually called from an asynchronous callback handler
      req.respond(ResponseString("test") ~> Ok)
  }
}
//then you can register this plan with jetty as usual
unfiltered.jetty.Server.http(8080).plan(AsyncPlan).run()
// #example1
}


locally {
// #example2
import unfiltered.response._
val hello = unfiltered.filter.Planify {
  case _ => ResponseString("hello world")
}
unfiltered.jetty.Server.anylocal.plan(hello).run { s =>
  unfiltered.util.Browser.open(
    s.portBindings.head.url
  )
}
// #example2
}


// #example3
import unfiltered.response._
val hello = unfiltered.netty.cycle.Planify {
  case _ => ResponseString("hello world")
}
unfiltered.netty.Server.http(8080).plan(hello).run()
// #example3

}
