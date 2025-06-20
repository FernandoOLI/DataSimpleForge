import config.JobConfig
import ingestion.OpenMeteoApiReader
import org.apache.spark.sql.SparkSession
import quality.SchemaValidator
import utils.{JobArgumentParser, Spark}
import writer.DeltaLakeWriter

object Main {
  def main(args: Array[String]): Unit = {
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
      DeltaLakeWriter.write(df, config.outputPath, Seq("year", "month", "day"))
    } else {
      println("Data Quality validation failed!")
    }
  }
}
