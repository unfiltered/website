object Example2 {

// #example1
import unfiltered.request._
import unfiltered.response._
val echo = unfiltered.filter.Planify {
  case Path(Seg(p :: Nil)) => ResponseString(p)
}
// #example1


// #example2
object Echo extends unfiltered.filter.Plan {
  def intent = {
    case Path(Seg(p :: Nil)) => ResponseString(p)
  }
}
// #example2


// #example3
unfiltered.jetty.Server.anylocal.plan(Echo).run()
// #example3


val myRenderer: String => Nothing = ???

// #example4
object Public extends unfiltered.filter.Plan {
  def intent = {
    case Path(Seg("admin" :: _)) => Pass
    case Path(Seg(path :: Nil)) => myRenderer(path)
  }
}
// #example4


// #example5
object MyPlan extends unfiltered.filter.Plan {
  def intent = publicIntent.onPass(privateIntent)

  val publicIntent = unfiltered.filter.Intent { ??? }
  val privateIntent = unfiltered.filter.Intent { ??? }
}
// #example5

}
