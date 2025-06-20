package utils

import config.JobConfig
import scopt.OParser

object JobArgumentParser {
  private val builder = OParser.builder[JobConfig]
  private val parser = {
    import builder._
    OParser.sequence(
      programName("DataSimpleForge"),
      head("DataSimpleForge", "1.0"),
      opt[String]("apiUrl")
        .required()
        .action((x, c) => c.copy(apiUrl = x))
        .text("API URL to fetch data"),
      opt[String]("outputPath")
        .required()
        .action((x, c) => c.copy(outputPath = x))
        .text("S3 path to save Delta output"),
      opt[String]("env")
        .optional()
        .action((x, c) => c.copy(env = x))
        .validate(x =>
          if (Set("local", "prod").contains(x)) success
          else failure("Env must be 'local' or 'prod'")
        )
        .text("Execution environment: local or prod")
    )
  }

  def parse(args: Array[String]): Option[JobConfig] = {
    OParser.parse(parser, args, JobConfig())
  }
}
