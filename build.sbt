
name := "CassandraSpark"

version := "1.0"

scalaVersion := "2.11.8"

resolvers ++= Seq("Typesafe Repository" at "http://repo.typesafe.com/typesafe/releases/",
  DefaultMavenRepository
)

libraryDependencies ++= {
  Seq(
    "org.apache.spark" %% "spark-core" % "2.0.2",
    "com.datastax.spark" %% "spark-cassandra-connector" % "2.0.0-M3",
    "com.typesafe" % "config" % "1.3.0",
    "org.apache.spark" %% "spark-sql" % "2.0.2"
  )
}

filterScalaLibrary := true

scalacOptions in Test ++= Seq("-Yrangepos")

scalacOptions := Seq("-feature", "-unchecked", "-deprecation", "-encoding", "utf8", "-target:jvm-1.8", "-Ydead-code")

javaOptions ++= Seq("-Xmx4G", "-Xms512M")

fork in run := true

fork in Test := true

assemblyMergeStrategy in assembly := customMergeStrategy

excludeFilter in unmanagedSources := HiddenFileFilter || "local.conf"


