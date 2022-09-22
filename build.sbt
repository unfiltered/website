enablePlugins(ParadoxPlugin)

enablePlugins(ParadoxSitePlugin)

enablePlugins(GhpagesPlugin)

scalaVersion := "2.13.9"

git.remoteRepo := "git@github.com:unfiltered/unfiltered.github.io.git"

com.typesafe.sbt.SbtGit.GitKeys.gitBranch := Some("master")

licenses := Seq("MIT" -> url("http://www.opensource.org/licenses/MIT"))

def unfilteredVersion = "0.10.4"

version := unfilteredVersion

Paradox / paradoxProperties ++= Map(
  "version" -> unfilteredVersion,
  "extref.unidoc.base_url" -> {
    // can't use @scaladoc due to https://github.com/lightbend/paradox/pull/77
    val sonatype = "https://oss.sonatype.org/service/local/repositories/releases/archive"
    val artifactId = "unfiltered-all_2.13"
    s"${sonatype}/ws/unfiltered/${artifactId}/${unfilteredVersion}/${artifactId}-${unfilteredVersion}-javadoc.jar/!/%s.html"
  }
)

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.13"

libraryDependencySchemes += "org.scala-lang.modules" %% "scala-xml" % "always"

libraryDependencies ++= Seq(
  "netty-uploads",
  "filter-uploads",
  "specs2",
  "scalatest",
  "filter-async",
  "agents",
  "directives",
  "json4s"
).map{ m =>
  "ws.unfiltered" %% s"unfiltered-${m}" % unfilteredVersion
}

paradoxTheme := Some(builtinParadoxTheme("generic"))

scalacOptions ++=
  Seq("-Xcheckinit", "-encoding", "utf8", "-deprecation", "-unchecked", "-feature")

val unusedWarnings = (
  "-Ywarn-unused:imports" ::
  Nil
)

scalacOptions ++= unusedWarnings

Seq(Compile, Test).flatMap(c =>
  c / console / scalacOptions --= unusedWarnings
)
