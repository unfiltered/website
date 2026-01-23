package example07

import unfiltered.request._
import unfiltered.directives._, Directives._
import unfiltered.directives.data.Requiring

object g {

def view(p: collection.Map[String, Seq[String]])(html: scala.xml.Elem) = ???

new unfiltered.filter.Planify({
// #example1
  case POST(Params(params)) =>
    case class BadParam(msg: String)
    extends ResponseJoiner(msg)( messages =>
      view(params)(<ul>{
        for (message <- messages)
        yield <li>{message}</li>
      }</ul>)
    )
// #example1


// #example2
    val inputString = data.as.String ~>
      data.as.String.trimmed ~>
      data.as.String.nonEmpty.fail(
        (key, _) => BadParam(s"$key is empty")
      )
// #example2


// #example3
    implicit def required[T]: Requiring[T, BadParam] =
      data.Requiring[T].fail(name =>
        BadParam(name + " is missing")
      )
// #example3


// #example4
    (inputString ~> data.Conditional(palindrome).fail(
      (_, value) => BadParam(s"'$value' is not a palindrome")
    ) ~> required named "palindrome")

    def palindrome(s: String) =
      s.toLowerCase.reverse == s.toLowerCase
// #example4
    ???
})

}
