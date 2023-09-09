name := "sensor-data"

version := "1.0"
resolvers += "Akka library repository".at("https://repo.akka.io/maven")

lazy val sensorData = (project in file("."))
  .enablePlugins(CloudflowApplicationPlugin, CloudflowAkkaPlugin)
  .settings(
    scalaVersion := "3.3.0",
    runLocalConfigFile := Some("src/main/resources/local.conf"),
    runLocalLog4jConfigFile := Some("src/main/resources/log4j.xml"),
    name := "sensor-data",

    libraryDependencies ++= Seq(
      Cloudflow.library.CloudflowAvro,
      "com.lightbend.akka" %% "akka-stream-alpakka-file" % "1.1.2" cross CrossVersion.for3Use2_13,
      "com.lightbend.cloudflow" %% "cloudflow-akka" % "2.3.2" cross CrossVersion.for3Use2_13,
      "com.typesafe.akka" %% "akka-http-spray-json" % "10.1.12" cross CrossVersion.for3Use2_13,
      "ch.qos.logback" % "logback-classic" % "1.2.11",
      "com.typesafe.akka" %% "akka-http-testkit" % "10.1.12" % "test" cross CrossVersion.for3Use2_13,
      "org.scalatest" %% "scalatest" % "3.0.8" % "test"
    )
  )

