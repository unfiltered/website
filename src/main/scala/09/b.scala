package example09

import unfiltered.request._
import unfiltered.response._
import unfiltered.jetty.SocketPortBinding
import example09.a.{Users, Auth}

object b {

// #example1
import unfiltered.Cookie

case class App(users: Users) extends
unfiltered.filter.Plan {
  def intent = Auth(users) {
    case Path("/") & Cookies(cookies) =>
      ResponseString(cookies("pref") match {
        case Some(c: Cookie) =>
          s"you pref ${c.value}, don't you?"
        case _ => "no preference?"
      })
    case Path("/prefer") & Params(p) =>
      // let's store it on the client
      SetCookies(Cookie("pref", p("pref")(0))) ~>
        Redirect("/")
    case Path("/forget") =>
      SetCookies(Cookie("pref", "")) ~>
        Redirect("/")
  }
}
// #example1


// #example2
object JimsAuth extends Users {
  def auth(u: String, p: String) =
    u == "jim" && p == "j@m"
}

val binding = SocketPortBinding(host = "localhost", port = 8080)

unfiltered.jetty.Server.portBinding(binding).plan(
  App(JimsAuth)
).run()
// #example2

}
