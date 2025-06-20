package quality
import org.apache.spark.sql.{DataFrame, SparkSession}
import org.apache.spark.sql.functions._
import org.slf4j.LoggerFactory

object RangeValidator {
  private val logger = LoggerFactory.getLogger(getClass)

  def validateNumericRanges(
      df: DataFrame
  )(implicit spark: SparkSession): DataFrame = {
    import spark.implicits._

    val invalidLatitude =
      df.filter(col("latitude").lt(-90) || col("latitude").gt(90))
    val invalidLongitude =
      df.filter(col("longitude").lt(-180) || col("longitude").gt(180))

    logger.info(s"Invalid latitude rows: ${invalidLatitude.count()}")
    logger.info(s"Invalid longitude rows: ${invalidLongitude.count()}")

    val cleanedDf = df
      .filter(col("latitude").between(-90, 90))
      .filter(col("longitude").between(-180, 180))

    cleanedDf
  }
}
