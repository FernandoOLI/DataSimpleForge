package unit

import org.apache.spark.sql.{Row, SparkSession}
import org.apache.spark.sql.types._
import org.scalatest.funsuite.AnyFunSuite
import quality.SchemaValidator
import utils.Spark

class SchemaValidatorTest extends AnyFunSuite {

  val spark: SparkSession = Spark.createSparkSession("local")

  test("Should detect schema mismatch") {
    val wrongSchemaDF = spark.createDataFrame(
      spark.sparkContext.parallelize(Seq(Row("invalid"))),
      StructType(Seq(StructField("wrong_column", StringType, nullable = true)))
    )
    assert(!SchemaValidator.validate(wrongSchemaDF))
  }
}
