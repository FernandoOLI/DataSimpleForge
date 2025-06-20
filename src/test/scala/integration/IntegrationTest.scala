package integration
import org.apache.commons.io.FileUtils
import java.io.File
import org.apache.spark.sql.SparkSession
import org.scalatest.funsuite.AnyFunSuite
import org.apache.spark.sql.functions._
import utils.Spark

import scala.io.Source

class IntegrationTest extends AnyFunSuite {

  val spark: SparkSession = Spark.createSparkSession("local")
  val tmpOutputPath = "./tmp/test_output_delta"
  import spark.implicits._

  test("Full ingestion pipeline should load, transform and write data") {
    try {
      val jsonString = Source.fromResource("open_meteo_sample.json").mkString
      val inputDF = spark.read.json(Seq(jsonString).toDS)

      assert(inputDF.count() == 1, "Should load one JSON object")

      val transformedDF = inputDF
        .withColumn("year", lit(2025))
        .withColumn("month", lit(6))
        .withColumn("day", lit(21))

      assert(transformedDF.columns.contains("year"))
      assert(transformedDF.columns.contains("hourly"))

      // ✅ Write to temporary Delta path
      transformedDF.write
        .format("delta")
        .mode("overwrite")
        .partitionBy("year", "month", "day")
        .save(tmpOutputPath)

      // ✅ Read back to validate write
      val readBackDF = spark.read.format("delta").load(tmpOutputPath)
      assert(readBackDF.count() > 0, "Written Delta table should not be empty")
    } finally {
      FileUtils.deleteDirectory(new File(tmpOutputPath))
    }
  }
}
