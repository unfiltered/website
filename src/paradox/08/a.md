Planning for Any-thing
----------------------

Thanks to one of the darker corners of the Scala type system
(*variance*), it's a cinch to define intents that work with all plans.

### Agnostic Intents

@@snip [ ](../../main/scala/08/a.scala) { #example1 }

The object `Hello` defines an intent with underlying request
and response types of `Any`. As a result, the intent can not
statically expect a particular underlying request or response
binding. This makes sense, as we want to make an intent that
works with any of them.

### Specific Plans

The next step is to supply the same generic intent to different kinds
of plans.

@@snip [ ](../../main/scala/08/a.scala) { #example2 }

As usual the plans are actual servlet filters or Netty handlers, so
you could use them with a server you have configured separately or
with a @ref:[server configured by Unfiltered][servers].

[servers]: ../03.md

