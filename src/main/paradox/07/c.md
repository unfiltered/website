Interpreter Reuse and Implicits
-------------------------------

### Explicit Interpreters

In a non-trival application you are likely to have more than one
parameter of one method that expects an integer. Instead of defining
interpreters inline, as in previous examples, you can define a general
interpreter once and use it many places.

@@snip [ ](../../scala/07/c.scala) { #example1 }

In the example above we explicitly reference and apply a single
interpreter to parameters "a" and "b", responding with their sum.

@@@ note
Unclear on the summing step? Values `a` and `b` are both of the
iterable type `Option[Int]`. We join these with `++` forming a
sequence of integers that could be as long as 2 or as short as 0,
depending on the input. The `sum` method on this sequence does what
you'd expect, and finally we join with a string.
@@@

### Implicit Interpreters

Referencing an interpreter was fairly tidy operation, but as we'll be
using interpreters often and in different applications, naming and
recalling names for various types could become tedious. Let's try it
with an implicit.

@@snip [ ](../../scala/07/c.scala) { #example2 }

The first thing you may notice is that `implyIntValue` is a bit
wordier than its predecessor. An implicit interpreter used for request
parameters **must interpret from Seq[String]**. Think of it as a full
interpretation from the input format to the output type. You may
define these for any output types you want, and import them into scope
wherever you want to use them. Interpreters like response functions
can be chained with `~>`, and so `data.as.String` is typically used at
the beginning of an interpreter to be used implicit.

If it seems like extra work to define implicit interpreters, keep
reading. The payoff comes with required parameters.
