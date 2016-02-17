name := "Akka-Http-Rest-Websocket-Swagger-Angular-Example"

version := "0.1"

scalaVersion := "2.11.7"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
    val akkaHttp = "2.4.2-RC2"
    Seq(
        "com.typesafe.akka" % "akka-http-experimental_2.11" % akkaHttp,
        "com.typesafe.akka" % "akka-http-spray-json-experimental_2.11" % akkaHttp,
        "org.scalatest" %% "scalatest" % "2.2.6" % "test",
        "org.scalactic" %% "scalactic" % "2.2.6",
        "com.typesafe.akka" % "akka-http-testkit-experimental_2.11" % akkaHttp,
        "com.github.swagger-akka-http" %% "swagger-akka-http" % "0.6.2",
        "org.scala-lang" % "scala-reflect" % scalaVersion.value,
        "org.slf4j" % "slf4j-api" % "1.7.7",
        "ch.qos.logback"  %  "logback-classic"   % "1.1.3",
        "com.typesafe.akka" % "akka-stream_2.11" % akkaHttp
    )
}

Revolver.settings
