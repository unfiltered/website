enablePlugins(ParadoxPlugin)

enablePlugins(ParadoxSitePlugin)

enablePlugins(GhpagesPlugin)

scalaVersion := "2.12.4"

git.remoteRepo := "git@github.com:unfiltered/unfiltered.github.io.git"

com.typesafe.sbt.SbtGit.GitKeys.gitBranch := Some("master")

licenses := Seq("MIT" -> url("http://www.opensource.org/licenses/MIT"))

version := "0.10.0-M1"

paradoxProperties in Paradox ++= Map(
  "version" -> version.value,
  "extref.unidoc.base_url" -> {
    // can't use @scaladoc due to https://github.com/lightbend/paradox/pull/77
    val sonatype = "https://oss.sonatype.org/service/local/repositories/releases/archive"
    val artifactId = "unfiltered-all_2.12"
    val v = version.value
    s"${sonatype}/ws/unfiltered/${artifactId}/${v}/${artifactId}-${v}-javadoc.jar/!/%s.html"
  }
)

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
  "ws.unfiltered" %% s"unfiltered-${m}" % version.value
}

paradoxTheme := Some(builtinParadoxTheme("generic"))

scalacOptions ++=
  Seq("-Xcheckinit", "-encoding", "utf8", "-deprecation", "-unchecked", "-feature", "-Ywarn-adapted-args")

val unusedWarnings = (
  "-Ywarn-unused" ::
  "-Ywarn-unused-import" ::
  Nil
)

scalacOptions ++= PartialFunction.condOpt(CrossVersion.partialVersion(scalaVersion.value)){
  case Some((2, v)) if v >= 11 => unusedWarnings
}.toList.flatten

Seq(Compile, Test).flatMap(c =>
  scalacOptions in (c, console) --= unusedWarnings
)
