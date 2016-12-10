Remembrance of Things Past
--------------------------

Basic authentication is a lightweight solution to general
authentication, but what if we need to remember a little more
information about a user's session? That's where
[cookies](http://en.wikipedia.org/wiki/HTTP_cookie) come in.

Let's build on our authenticated application and add support for simple cookie handling.

@@snip [ ](../../main/scala/09/b.scala) { #example1 }

Now that we have a slightly more sophisitcated basic application let's mount it with a user named `jim` and a password of `j@m`.

@@snip [ ](../../main/scala/09/b.scala) { #example2 }

In your browser, open the url `http://localhost:8080/` and you should
be greeted with its native authentication dialog. Enter `jim` and
`j@m`, if you are feeling authentic.

Once authenticated you should see simple text questioning your
preferences. Why is this? Well, you have yet to tell the server what
you prefer. In your url bar, enter the address

```text
http://localhost:8080/prefer?pref=kittens
```

or whatever else you have a preference for. Now, every time you
request `http://localhost:8080/` the server has remembered your
preference for you. This is a cookie at work!

If you change your mind you can always hit the `prefer` path with a
new pref or just tell the server to forget it by entering the address
`http://localhost:8080/forget`.
