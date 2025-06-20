package quality

import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._
import java.time.Instant

case class IngestionMetrics(
                             executionDate: String,
                             totalRecords: Long,
                             validRecords: Long,
                             invalidRecords: Long,
                             durationSeconds: Long,
                             sourceUrl: String
                           ) {

  def toDataFrame(spark: SparkSession): DataFrame = {
    import spark.implicits._

    Seq(this).toDF()
  }

}

object IngestionMetrics {

  private var startTime: Long = 0

  def startTimer(): Unit = {
    startTime = System.currentTimeMillis()
  }

  def stopTimer(): Long = {
    (System.currentTimeMillis() - startTime) / 1000
  }

  def generate(
                executionDate: String,
                totalRecords: Long,
                validRecords: Long,
                invalidRecords: Long,
                sourceUrl: String
              ): IngestionMetrics = {
    val duration = stopTimer()

    IngestionMetrics(
      executionDate = executionDate,
      totalRecords = totalRecords,
      validRecords = validRecords,
      invalidRecords = invalidRecords,
      durationSeconds = duration,
      sourceUrl = sourceUrl
    )
  }
}