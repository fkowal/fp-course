buildscript {
    dependencies {
        classpath 'cz.alenkacz:gradle-scalafmt:1.7.0'
    }
}

plugins {
    id 'java'
    id 'scala'
    id 'application'
    id 'maven-publish'
    id 'com.github.maiflai.scalatest' version '0.23'
    id 'jacoco'
}

apply plugin: 'scalafmt'

sourceCompatibility = 1.8

project.group = 'fp'

repositories {
    mavenCentral()
}
ext {
    scalaVersion = '2.13.0-M5'
}

dependencies {
    // scala
    compile group: 'org.scala-lang',               name: 'scala-library',                 version: '2.13.0-M5'
    compile group: 'org.scala-lang',               name: 'scala-reflect',                 version: '2.13.0-M5'
    compile group: "org.springframework.boot",     name: "spring-boot-starter-webflux",   version: "2.1.0.RELEASE"
    compile group: "com.fasterxml.jackson.module", name: "jackson-module-scala_"+scalaVersion,     version: "2.9.8"

    compile group: "org.typelevel", name: 'cats-core_'+scalaVersion, version: "1.5.0"
    compile group: "com.chuusai", name: 'shapeless_'+scalaVersion, version: "2.3.3"
    compile group: "com.github.mpilquist", name: "simulacrum_"+scalaVersion, version: "0.15.0"

//    compile "org.scala-lang:scala-compiler:2.12.8"
//    testCompile group: "com.lihaoyi", name: "ammonite_2.12.8", version: "1.6.0"
    testCompile group: "org.scalatest", name: 'scalatest_'+scalaVersion, version: "3.0.6-SNAP5"
    testRuntime group: 'org.pegdown', name: 'pegdown', version: '1.6.0'
}

//mainClassName = 'fp.spring.spring4.UserApp'
//mainClassName = 'fp.spring.spring5.Spring5App'
//mainClassName = 'fp.spring.springfp.Spring5Fp'
mainClassName = 'fp.spring.springfp.SpringWebFluxApp'

compileJava.options.compilerArgs += '-parameters'

tasks.withType(ScalaCompile) {
    scalaCompileOptions.deprecation = false
    scalaCompileOptions.additionalParameters = [
            // @see https://tpolecat.github.io/2017/04/25/scalac-flags.html
            "-deprecation",                      // Emit warning and location for usages of deprecated APIs.
            "-encoding", "utf-8",                // Specify character encoding used by source files.
            "-explaintypes",                     // Explain type errors in more detail.
            "-feature",                          // Emit warning and location for usages of features that should be imported explicitly.
            "-language:existentials",            // Existential types (besides wildcard types) can be written and inferred
            "-language:experimental.macros",     // Allow macro definition (besides implementation and application)
            "-language:higherKinds",             // Allow higher-kinded types
            "-language:implicitConversions",     // Allow definition of implicit functions called views
            "-unchecked",                        // Enable additional warnings where generated code depends on assumptions.
            "-Xcheckinit",                       // Wrap field accessors to throw an exception on uninitialized access.
//  "-Xfatal-warnings",                  // Fail the compilation if there are any warnings.
            "-Xfuture",                          // Turn on future language features.
            "-Xlint:adapted-args",               // Warn if an argument list is modified to match the receiver.
            "-Xlint:constant",                   // Evaluation of a constant arithmetic expression results in an error.
            "-Xlint:delayedinit-select",         // Selecting member of DelayedInit.
            "-Xlint:doc-detached",               // A Scaladoc comment appears to be detached from its element.
            "-Xlint:inaccessible",               // Warn about inaccessible types in method signatures.
            "-Xlint:infer-any",                  // Warn when a type argument is inferred to be `Any`.
            "-Xlint:missing-interpolator",       // A string literal appears to be missing an interpolator id.
            "-Xlint:nullary-override",           // Warn when non-nullary `def f()' overrides nullary `def f'.
            "-Xlint:nullary-unit",               // Warn when nullary methods return Unit.
            "-Xlint:option-implicit",            // Option.apply used implicit view.
            "-Xlint:package-object-classes",     // Class or object defined in package object.
            "-Xlint:poly-implicit-overload",     // Parameterized overloaded implicit methods are not visible as view bounds.
            "-Xlint:private-shadow",             // A private field (or class parameter) shadows a superclass field.
            "-Xlint:stars-align",                // Pattern sequence wildcard must align with sequence component.
            "-Xlint:type-parameter-shadow",      // A local type parameter shadows a type already in scope.
//  "-Ywarn-dead-code",                  // Warn when dead code is identified.
            "-Ywarn-extra-implicit",             // Warn when more than one implicit parameter section is defined.
            "-Ywarn-numeric-widen",              // Warn when numerics are widened.
//  "-Ywarn-unused:implicits",           // Warn if an implicit parameter is unused.
//  "-Ywarn-unused:imports",             // Warn if an import selector is not referenced.
            "-Ywarn-unused:locals",              // Warn if a local definition is unused.
//  "-Ywarn-unused:params",              // Warn if a value parameter is unused.
            "-Ywarn-unused:patvars",             // Warn if a variable bound in a pattern is unused.
            "-Ywarn-unused:privates",            // Warn if a private member is unused.
            "-Ywarn-value-discard",               // Warn when non-Unit expression results are unused.
            "-Ymacro-annotations"                 // enable simulacrum typeclass annotations only in 2.13

            // 2.12 options
            //  "-Xlint:by-name-right-associative",  // By-name parameter of right associative operator.
            //  "-Xlint:unsound-match",              // Pattern match may not be typesafe.
            //  "-Yno-adapted-args",                 // Do not adapt an argument list (either by inserting () or creating a tuple) to match the receiver.
            //  "-Ypartial-unification",             // Enable partial unification in type constructor inference
            //  "-Ywarn-inaccessible",               // Warn about inaccessible types in method signatures.
            //  "-Ywarn-infer-any",                  // Warn when a type argument is inferred to be `Any`.
            //  "-Ywarn-nullary-override",           // Warn when non-nullary `def f()' overrides nullary `def f'.
            //  "-Ywarn-nullary-unit",               // Warn when nullary methods return Unit.
    ]
}

test {
    maxParallelForks = 1
    testLogging {
        exceptionFormat = 'full'
    }
}

//task repl(type:JavaExec) {
//    mainClassName = "scala.tools.nsc.MainGenericRunner"
//    main = "scala.tools.nsc.MainGenericRunner"
//    classpath = sourceSets.main.runtimeClasspath
//    standardInput System.in
//    args '-usejavacp'
//}

wrapper {
    gradleVersion = '5.1'
}
