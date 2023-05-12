Request Matchers
----------------

### Methods and Paths

Unfiltered supplies a wide range of *request matchers*--extractor
objects that work against requests--from path segments to HTTP methods
and headers. Applications use request matchers to define whether and
how they will respond to a request.

@@snip [ ](../../scala/06/a.scala) { #example1 }

This case will match GET requests to the path `/record/1`. To match
against path segments, we can nest one additional extractor:

@@snip [ ](../../scala/06/a.scala) { #example2 }

This matches any `id` string that is directly under the record
path. The `Seg` extractor object matches against the `String` type and
it is typically nested under a `Path` matcher. `Seg` extracts a lists
of strings from the supplied string, separated into path segments by
forward-slashes.

### Reading Requests and Delayed Matching

The above case clause matches a request to get a record. What about
putting them?

@@snip [ ](../../scala/06/a.scala) { #example3 }

Access to the request body generally has side effects, such as the
consumption of a stream that can only be read once. For this reason
the body is not accessed from a request matcher, which could be
evaluated more than one time, but from utility functions that operate
on the request object.

In this case, we assigned a reference to the request using `req @` and
then read its body into a byte array--on the assumption that its body
will fit into available memory. That aside, a minor annoyance is that
this code introduces some repetition in the matching expression.

@@snip [ ](../../scala/06/a.scala) { #example4 }

An alternative is to match first on the path, then on the method:

@@snip [ ](../../scala/06/a.scala) { #example5 }

This approach eliminates the duplicated code, but it's important to
recognize that it behaves differently as well. The original intent
partial function was simply *not defined* for request to that path
that were not a GET or a PUT. The latest one matches any request to
that path, and therefore it must return a value for all methods with
its match expression.

Importantly, delaying the match on request method simplified the
intent partial function. What used to be two cases is now one, and we
could add support for other methods like DELETE without adding any
complexity to its pattern matching. This is worth noting because
`ifDefined` is called on every intent at least once prior to its
evaluation. By making the intent more broadly defined, we've reduced
the complexity of that and potentially improved runtime performance.
