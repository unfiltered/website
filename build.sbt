enablePlugins(ParadoxPlugin)

enablePlugins(ParadoxSitePlugin)

enablePlugins(GhpagesPlugin)

scalaVersion := "3.8.3-RC1"

git.remoteRepo := "git@github.com:unfiltered/unfiltered.github.io.git"

com.github.sbt.git.SbtGit.GitKeys.gitBranch := Some("master")

licenses := Seq("MIT" -> url("https://www.opensource.org/licenses/MIT"))

def unfilteredVersion = "0.12.1"

version := unfilteredVersion

Paradox / paradoxProperties ++= Map(
  "version" -> unfilteredVersion,
)

libraryDependencies += "org.scalatest" %% "scalatest" % "3.2.19"

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

scalacOptions ++= Seq(
  "-encoding",
  "utf8",
  "-deprecation",
  "-unchecked",
  "-feature",
)

val unusedWarnings = Seq(
  "-Wunused:imports",
)

scalacOptions ++= unusedWarnings

Seq(Compile, Test).flatMap(c =>
  c / console / scalacOptions --= unusedWarnings
)
