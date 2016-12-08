package example06

object d {
// #example1
import unfiltered.request._
import unfiltered.response._

object SillyStore extends unfiltered.filter.Plan {
  @volatile private var store = Map.empty[String, Array[Byte]]
  def intent = {
    case req @ Path(Seg("record" :: id :: Nil)) => req match {
      case GET(_) =>
        store.get(id).map(ResponseBytes).getOrElse {
          NotFound ~> ResponseString("No record: " + id)
        }
      case PUT(_) =>
        SillyStore.synchronized {
          store = store + (id -> Body.bytes(req))
        }
        Created ~> ResponseString("Created record: " + id)
      case _ =>
        MethodNotAllowed ~> ResponseString("Must be GET or PUT")
    }
  }
}
// #example1


// #example2
unfiltered.jetty.Server.local(8080).plan(SillyStore).run()
// #example2

}
