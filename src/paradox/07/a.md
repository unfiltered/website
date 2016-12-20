Directive Intent
----------------

Within an Unfiltered plan, an intent function maps from request
objects to response functions. With directives, you define a partial
function mapping from requests to a `Result`. We compose this function
with another, which produces the standard intent function understood
by Unfiltered.

@@@ note
Directives are an example of an Unfiltered
@ref:[kit](../08/b.md), set of tools providing a higher
level of abstraction over the core library.
@@@

### Selective enforcement

You can define a directive function using familiar request
extractors. Let's start with a raw intent function.

@@snip [ ](../../main/scala/07/a.scala) { #example1 }

You can use curl to inspect the different responses:

```sh
curl -v http://localhost:8080/ -H "Accept: application/json"
curl -v http://localhost:8080/
```

The 404 response page to the second request is not so great. With
directives, we'll do better than that *by default*.

@@snip [ ](../../main/scala/07/a.scala) { #example2 }

And with that you'll see a 406 Not Acceptable response when appropriate.

Directives results are typically, but not necessarily, defined in a
single for expression. In the expression above we discard the result
value of the `Accepts.Json` directive, as we did previously with the
extractor, and map it to our dummy response function.

The 406 error response is produced by the directive itself,
encapsulating behavior that can be used everywhere. What if you want
different error behavior? Make your own directive! (Seriously, you'll
see how on the next page.)

### One true path

You may have noticed that directives transfer (and enrich) routing
logic from extractors. If your extractors are reduced to the task of
matching against paths alone, you can even eliminate those.

@@snip [ ](../../main/scala/07/a.scala) { #example3 }

It looks pretty different, but remember that here still composes to a
standard Unfiltered intent function.
