package example08

import unfiltered.response._
import unfiltered.request._

object b {

// #example1
object GZip extends unfiltered.kit.Prepend {
  def intent = unfiltered.Cycle.Intent[Any,Any] {
    case Decodes.GZip(req) =>
      ContentEncoding.GZip ~> ResponseFilter.GZip
  }
}
// #example1


// #example2
object EchoPlan extends unfiltered.filter.Plan {
  def intent = unfiltered.kit.GZip {
    case Path(path) => ResponseString(path)
  }
}
// #example2

}
