import config.{JobConfig, PathConfig}
import ingestion.OpenMeteoApiReader
import org.apache.spark.sql.SparkSession
import quality.{IngestionMetrics, RangeValidator, SchemaValidator}
import utils.{JobArgumentParser, Spark}
import writer.DeltaLakeWriter
import org.slf4j.LoggerFactory

import java.time.LocalDate

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
    val paths = PathConfig.createPaths(config.path)
    val today = LocalDate.now().toString
    val df = OpenMeteoApiReader.read(config.apiUrl, today)
    if (SchemaValidator.validate(df)) {
      val (validatedDf, invalidDF) = RangeValidator.validateNumericRanges(df)
      DeltaLakeWriter.write(
        validatedDf,
        paths.output,
        Seq("year", "month", "day")
      )
      DeltaLakeWriter.write(
        invalidDF,
        paths.badData,
        Seq("year", "month", "day")
      )
      val metrics = IngestionMetrics.generate(
        executionDate = today,
        totalRecords = df.count(),
        validRecords = validatedDf.count(),
        invalidRecords = invalidDF.count(),
        sourceUrl = config.apiUrl
      )
      DeltaLakeWriter.write(
        metrics.toDataFrame(spark),
        paths.metrics,
        List()
      )
    } else {
      logger.error("Data Quality validation failed!")
    }
  }
}
