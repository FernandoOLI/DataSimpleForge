import config.JobConfig
import ingestion.OpenMeteoApiReader
import org.apache.spark.sql.SparkSession
import quality.SchemaValidator
import utils.{JobArgumentParser, Spark}
import writer.DeltaLakeWriter
import org.slf4j.LoggerFactory

object Main {
  private val logger = LoggerFactory.getLogger(getClass)
  def main(args: Array[String]): Unit = {
    logger.info("Starting job...")
    JobArgumentParser.parse(args) match {
      case Some(config) =>
        run(config)
      case None =>
        System.exit(1)
    }
  }

  private def run(config: JobConfig): Unit = {
    implicit val spark: SparkSession = Spark.createSparkSession(config.env)

    val df = OpenMeteoApiReader.read(config.apiUrl)
    df.printSchema()
    if (SchemaValidator.validate(df)) {
      DeltaLakeWriter.write(df, config.outputPath, Seq("year", "month", "day"))
    } else {
      logger.error("Data Quality validation failed!")
    }
  }
}
