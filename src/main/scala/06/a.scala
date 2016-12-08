package example06

import unfiltered.request._

object a extends unfiltered.filter.Planify({

// #example1
case GET(Path("/record/1")) => ???
// #example1


// #example2
case GET(Path(Seg("record" :: id :: Nil))) => ???
// #example2


// #example3
case req @ PUT(Path(Seg("record" :: id :: Nil))) =>
  val bytes = Body.bytes(req)
// #example3
  ???


// #example4
case GET(Path(Seg("record" :: id :: Nil))) => ???
case req @ PUT(Path(Seg("record" :: id :: Nil))) => ???
// #example4


// #example5
case req @ Path(Seg("record" :: id :: Nil)) => req match {
  case GET(_) => ???
  case PUT(_) => ???
  case _ => ???
}
// #example5

})
