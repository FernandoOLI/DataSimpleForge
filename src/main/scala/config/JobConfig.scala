package config

case class JobConfig(
                      apiUrl: String = "",
                      path: String = ".tmp/data",
                      env: String = "local"
)
