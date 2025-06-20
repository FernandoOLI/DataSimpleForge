package unit

import org.apache.spark.sql.SparkSession
import org.scalatest.funsuite.AnyFunSuite
import transformation.PartitionTransformer
import utils.Spark

import java.time.LocalDate

class TransformationTest extends AnyFunSuite {

  val spark: SparkSession = Spark.createSparkSession("local")

  import spark.implicits._

  test("Should add year, month, day columns") {
    val df = Seq("dummy").toDF("col")

    val resultDF = PartitionTransformer.addDatePartitions(
      df,
      LocalDate.of(2025, 6, 19).toString
    )

    val row = resultDF.first()
    assert(row.getAs[Int]("year") == 2025)
    assert(row.getAs[Int]("month") == 6)
    assert(row.getAs[Int]("day") == 19)
  }
}
