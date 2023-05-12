Response Functions
------------------

### Response Function and Combinators

With a typical request-response cycle intent, the partial function's
return value is of Unfiltered's type `ResponseFunction`. A response
function takes a response object, presumably mutates it, and returns
the same response object.

Unfiltered includes a number of response functions for common response
types. Continuing the "record" example, in some cases we may want to
respond with a particular string:

@@snip [ ](../../scala/06/c.scala) { #example1 }

We should also set a status code for this response. Fortunately there
is a predefined function for this too, and response functions are
easily composed. Unfiltered even supplies a chaining combinator `~>`
to make it pretty:

@@snip [ ](../../scala/06/c.scala) { #example2 }

If we had some bytes, they would be as easy to serve as strings:

@@snip [ ](../../scala/06/c.scala) { #example3 }

Passing or Handling Errors
--------------------------

And finally, for the case of unexpected methods we have a few
choices. One option is to *pass* on the request:

@@snip [ ](../../scala/06/c.scala) { #example4 }

The `Pass` response function is a signal for the plan act as if the
request was not defined for this intent. If no other plan responds to
the request, the server may respond with a 404 eror. But we can
improve on that by ensuring that any request to this path that is not
an expected method receives an appropriate response:

@@snip [ ](../../scala/06/c.scala) { #example5 }
