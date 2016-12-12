import sbt.Build
import sbtassembly.{MergeStrategy, PathList}

object MyBuild extends Build {
  val customMergeStrategy: String => MergeStrategy = {
    case PathList("local.conf") => MergeStrategy.discard
    case PathList("org", "apache", "spark", "unused", "UnusedStubClass.class") => MergeStrategy.discard
    case PathList("org", "apache", "commons", xs @ _ *) => MergeStrategy.last
    case PathList("org", "apache", "hadoop", "yarn", xs @ _ *) => MergeStrategy.last
    case PathList("org", "apache", "spark", xs @ _ *) => MergeStrategy.last
    case PathList("org", "aopalliance", xs @ _ *) => MergeStrategy.last
    case PathList("javax","inject", xs @ _ *) => MergeStrategy.last
    case s =>
      MergeStrategy.defaultMergeStrategy(s)
  }
}