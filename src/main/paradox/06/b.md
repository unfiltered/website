Within the Parameters
---------------------

### Extracting Params

Requests are commonly accompanied by named parameters, either in the
[query string][query] of the URL or in the body of a
[POST][post]. Unfiltered supports access to these parameters with the
`Params` extractor.

[query]: https://en.wikipedia.org/wiki/Query_string
[post]: https://en.wikipedia.org/wiki/POST_%28HTTP%29#Use_for_submitting_web_forms

@@snip [ ](../../scala/06/b.scala) { #example1 }

The type of `params` extracted is `Map[String, Seq[String]]`, with a
default value of `Seq.empty`. With this interface, it is always safe
to apply the map. But keep in mind that parameters may be specified
with no value, and may occur multiple times. The `Params` extractor
returns empty strings for named parameters with no value, as many
times in the sequence as they occurred in the request.

For example, the query string `?test&test=3&test` produces the
sequence of strings `"", "3", ""`. You can check this yourself by
querying the plan defined above:

@@snip [ ](../../scala/06/b.scala) { #example2 }

### Routing by Parameter

While the `Params` extractor is useful for accessing parameters, it
doesn't provide any control flow to an intent partial function.

If you want to route requests based on the parameters present, you'll
need to nest a custom extractor inside `Params`. For this Unfiltered
provides a `Params.Extract` base class:


@@snip [ ](../../scala/06/b.scala) { #example3 }

The above extractor will match on the first parameter named "test" in
a request. If no parameters are named test, the pattern does not
match. However, a named parameter with no value would match. We can
exclude this possibility with a richer definition.

@@snip [ ](../../scala/06/b.scala) { #example4 }

The second parameter of the `Params.Extract` constructor is a function
`Seq[String] => Option[B]`, with `B` being the type extracted. The
values `first` and `nonempty` are functions defined in the `Params`
object that may be chained together with `~>` (as with response
functions).

Typically the chain begins with `first`, to require at least one
parameter of the given name and discard the rest. Subsequent functions
may require a `nonempty` value as above, or produce a `trimmed`
string, or an `int` value from the string.

When a parameter transformation function fails, `None` is produced and
the extractor does not match for the request. Knowing this, you can
write your own transformation functions using `map` and `filter`.

@@snip [ ](../../scala/06/b.scala) { #example5 }

There's also a convenience function to simplify the definition of
transformation *predicates*.

@@snip [ ](../../scala/06/b.scala) { #example6 }

Try it all out in this server, which returns 404 unless provided with
a "pos" parameter that is a positive integer, and "neg" that is
negative.

@@snip [ ](../../scala/06/b.scala) { #example7 }

@@@ note
The `&` extractor matches when the extractor to its left and right
match, in this case to require multiple parameters.
@@@
