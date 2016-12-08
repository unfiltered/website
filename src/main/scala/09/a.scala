package example09

object a {

// #example1
trait Users {
  def auth(u: String, p: String): Boolean
}

import unfiltered.request._
import unfiltered.response._
import unfiltered.Cycle

case class Auth(users: Users) {
  def apply[A,B](intent: Cycle.Intent[A,B]) =
    Cycle.Intent[A,B] {
      case req@BasicAuth(user, pass) if(users.auth(user, pass)) =>
        Cycle.Intent.complete(intent)(req)
      case _ =>
        Unauthorized ~> WWWAuthenticate("""Basic realm="/"""")
    }
}
// #example1


// #example2
case class App(users: Users) extends
unfiltered.filter.Plan {
  def intent = Auth(users) {
    case _ => ResponseString("Shhhh!")
  }
}
// #example2

}
