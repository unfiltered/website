Trying Netty
------------

As with the Jetty server used for earlier console hacking, for Netty
we also need a starter project. If you don't have the `g8` command line
tool installed, please go @ref:[back to that page until you do][jetty].

[jetty]: ../01.md

### Enter the Console

This step will fetch a number of dependencies and sometimes certain
repositories are a little wonky, so cross your fingers.

    g8 unfiltered/unfiltered-netty --name=nettyplayin
    cd nettyplayin
    sbt console

Once you do get to a console, this should just work:

@@snip [ ](../../main/scala/10/a.scala) { #example1 }

Direct a web browser to [http://127.0.0.1:8080/][local] and you'll
be in *hello world* business.

[local]: http://127.0.0.1:8080/
