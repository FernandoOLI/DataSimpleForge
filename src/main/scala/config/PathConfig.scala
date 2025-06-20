package config

object PathConfig {

  case class OutputPaths(output: String, metrics: String, badData: String)

  def createPaths(basePath: String): OutputPaths = {
    val normalizedBase =
      if (basePath.endsWith("/")) basePath.dropRight(1) else basePath

    OutputPaths(
      output = s"$normalizedBase/output",
      metrics = s"$normalizedBase/metrics",
      badData = s"$normalizedBase/bad_data"
    )
  }

}
