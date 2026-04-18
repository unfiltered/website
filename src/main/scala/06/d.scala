package example06

object d {
// #example1
import unfiltered.request._
import unfiltered.response._
import scala.collection.concurrent.TrieMap

object SillyStore extends unfiltered.filter.Plan {
  private val store: TrieMap[String, Array[Byte]] =
    TrieMap.empty[String, Array[Byte]]

  def intent = {
    case req @ Path(Seg("record" :: id :: Nil)) => req match {
      case GET(_) =>
        store.get(id).map(ResponseBytes.apply).getOrElse {
          NotFound ~> ResponseString("No record: " + id)
        }
      case PUT(_) =>
        store += (id -> Body.bytes(req))
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
