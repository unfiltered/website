Required Parameters
-------------------

So far we've learned know how to define implicit interpreters from a
sequence of string to any type T and use them with
`data.as.Option[T]`. Now we'll see how to use the same interpreters
for required parameters.

### Your "required" Function

The failure to supply a required parameter must produce an application
defined error response. We'll define a very simple one.

@@snip [ ](../../scala/07/d.scala) { #example1 }

The name of the function is not important when used implicitly, but
call it `required` is a good convention since you may also want to use
it explicitly when defining interpreters inline.

### Using "required" Implicitly

With a required function in scope we can use it with any implicit
interpreters also in scope. The `data.as.String` interpreter is
imported from the `Directives` object, so we can use it immediately.

@@snip [ ](../../scala/07/d.scala) { #example2 }

Let's examine the output from this one with curl:

```sh
$ curl -v http://127.0.0.1:8080/ -d opt=hello -d req=world
opt: Some(hello) req: world
```

Since `req` is required to produce a success response, it's not
wrapped in `Option` or anything else; the type of the bound value is
whatever its interpreter produces.

### Using "required" Explicitly

Most interpreters work with an option of the data so that they can be
chained together in support of both optional and required parameters.
Required is itself an interpreter which unboxes from the `Option`, so
it generally must be the last interpreter in a chain.

@@snip [ ](../../scala/07/d.scala) { #example3 }

This service returns the last digit of the required provided
integer. Since we didn't provide a `fail` handler for
`data.as.BigInt`, it falls to `required` to produce a failure
response.

```sh
$ curl http://127.0.0.1:8080/ -d in=1334534
4
$ curl http://127.0.0.1:8080/ -d in=1334534a
in is missing
```

To be more specific, we can supply a failure to the integer
interpreter.

@@snip [ ](../../scala/07/d.scala) { #example4 }

Now each failure condition produces a distinct error.

```sh
$ curl http://127.0.0.1:8080/ -d in=1334534a
'1334534a' is not a valid int for in
$ curl http://127.0.0.1:8080/
in is missing
```
