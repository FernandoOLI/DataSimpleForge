package config

case class JobConfig(
    apiUrl: String = "",
    outputPath: String = ".tmp/data",
    env: String = "local"
)
