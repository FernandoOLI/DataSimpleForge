import config.JobConfig
import ingestion.OpenMeteoApiReader
import org.apache.spark.sql.SparkSession
import quality.{RangeValidator, SchemaValidator}
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
    if (SchemaValidator.validate(df)) {
      val validatedDf = RangeValidator.validateNumericRanges(df)
      DeltaLakeWriter.write(validatedDf, config.outputPath, Seq("year", "month", "day"))
    } else {
      logger.error("Data Quality validation failed!")
    }
  }
}
