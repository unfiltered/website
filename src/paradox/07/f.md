Interpreters of Your Own Design
-------------------------------

Unfiltered comes with a small number of interpreters for standard
Scala types. We may have left out the one you want (send a pull
request), or perhaps you'd like to define interpreters for a type
specific to your own application. This is in fact a very good idea!

### Conditional Interpreters

A simple way to augment an interpreter is to require a condition for
which it is valid. For example, in your application valid identifiers
might be non-negative, non-zero integers up to a certain size. Another
application might permit a certain set of characters as identifiers.

Rather than attempting to anticipate all the ways you might like to
constrain input parameters, Unfiltered leaves it to you to define
interpreters using the Scala language. Here, we'll define a simple one
that accepts only even integers.

@@snip [ ](../../main/scala/07/f.scala) { #example1 }

As a conditional interpreter of integer, `evenInt` inputs and outputs
an integer, failing with an error if it doesn't satisfy the
condition. To make it work seamlessly with request parameters we may
define an implicit integer interpreter.

@@snip [ ](../../main/scala/07/f.scala) { #example2 }

Then it's just a matter of using `evenInt` like any other interpreter.

@@snip [ ](../../main/scala/07/f.scala) { #example3 }

### Fallible Interpreters

Conditional interpreters are great for adding application-specific
constraints to existing interpreters, but often you'll need to create
interpreters for your own types. These are known as *fallible*
interpreters, under the assumption that any conversion may fail.

You can make a fallible interpreter to any type. We'll demonstrate
with a simple typed data store.

@@snip [ ](../../main/scala/07/f.scala) { #example4 }

The `data.Fallible` case class takes a function from its input type to
an option of its output type; `Map#get` fits the bill perfectly. Then
we defined a complete, error-capable interpreter so that it's easy and
clean to turn input parameters into tools.

@@snip [ ](../../main/scala/07/f.scala) { #example5 }

Let's see how it works:

```sh
curl http://127.0.0.1:8080/  -d id=3
Some(Tool(Scissors))
```

With a parameter directive you can interpret the input into any type
you like. We used an in-memory map, but it could just as easily be an
entity loaded from external storage.
