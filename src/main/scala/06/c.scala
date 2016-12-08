package example06

import unfiltered.request._
import unfiltered.response._

object c {
  val bytes: Array[Byte] = ???

new unfiltered.filter.Planify({
// #example1
  case PUT(_) =>
    // ...
    ResponseString("Record created")
// #example1
})


new unfiltered.filter.Planify({
// #example2
  case PUT(_) =>
    // ...
    Created ~> ResponseString("Record created")
// #example2
})


new unfiltered.filter.Planify({
// #example3
  case GET(_) =>
    // ...
    ResponseBytes(bytes)
// #example3
})


new unfiltered.filter.Planify({
// #example4
  case _ => Pass
// #example4
})


new unfiltered.filter.Planify({
// #example5
  case _ => MethodNotAllowed ~>
              ResponseString("Must be GET or PUT")
// #example5
})

}
