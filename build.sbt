import sbt._

resolvers += Resolver.sonatypeRepo("releases")
resolvers += Resolver.sonatypeRepo("snapshots")

scalaVersion := "2.12.1"

val monocleVersion = "1.4.0"

val scalazVersion = "7.2.7"

val matryoshkaVersion = "0.18.3"

val catsVersion = "0.9.0"

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats" % "0.9.0",
  "com.chuusai" %% "shapeless" % "2.3.2",
  "com.lihaoyi" % "ammonite" % "0.8.2" % "test" cross CrossVersion.full

//  "com.github.julien-truffaut"  %%  "monocle-core"    % monocleVersion,
//  "com.github.julien-truffaut"  %%  "monocle-generic" % monocleVersion,
//  "com.github.julien-truffaut"  %%  "monocle-macro"   % monocleVersion,
//  "com.github.julien-truffaut"  %%  "monocle-state"   % monocleVersion,
//  "com.github.julien-truffaut"  %%  "monocle-refined" % monocleVersion,
//  "com.github.julien-truffaut"  %%  "monocle-law"     % monocleVersion % "test",
//  "org.scalaz"                  %%  "scalaz-core"     % scalazVersion,
//  "com.slamdata"                %%  "matryoshka-core" % matryoshkaVersion
)
val start =
  """
    | println("witam")
    |
  """.stripMargin

initialCommands in console := start

// for @Lenses macro support
addCompilerPlugin("org.scalamacros" %% "paradise" % "2.1.0" cross CrossVersion.full)

// For kind projector, `Either[String, ?]`
addCompilerPlugin("org.spire-math" %% "kind-projector" % "0.9.3")
