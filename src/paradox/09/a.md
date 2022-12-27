Who's Who
---------

Out of the box HTTP provides you with [basic authentication][basic],
a simple way to specify a name and password for a request. These
credentials are transferred as an unencrypted request header, so
applications should secure both credentials and message bodies by
requiring HTTPS for any protected resources.

[basic]: https://en.wikipedia.org/wiki/Basic_access_authentication

Below, we define a *kit* that extracts a username and password via
basic HTTP authentication and verifies those credentials before
letting anyone through the gate. It presumes a `Users` service that
would validate the user's credentials.

@@snip [ ](../../main/scala/09/a.scala) { #example1 }

By applying this kit we can layer basic authentication around any
intent in a client application.

@@snip [ ](../../main/scala/09/a.scala) { #example2 }

Also, don't give the password to any newspaper reporters.
