name := "Scala-Lucene"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies += "org.apache.lucene" % "lucene-core" % "5.2.1"
libraryDependencies += "org.apache.lucene" % "lucene-analyzers-common" % "5.2.1"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.4.1"
libraryDependencies += "org.apache.lucene" % "lucene-queryparser" % "5.2.1"
libraryDependencies ++= Seq("org.specs2" %% "specs2-core" % "3.6.1" % "test")

resolvers += "scalaz-bintray" at "http://dl.bintray.com/scalaz/releases"

scalacOptions in Test ++= Seq("-Yrangepos")
